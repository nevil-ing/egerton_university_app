import axios from 'axios'
import * as cheerio from 'cheerio'
import path from 'path'
import { Element } from 'domhandler'

interface NewsItem {
  title: string
  link: string
  imageUrl: string | null
  date: string
  intro: string
  scrapedAt: number
}

interface DownloadItem {
  title: string
  link: string
  format: string
  scrapedAt: number
}

const delay = (ms: number) => new Promise(resolve => setTimeout(resolve, ms))

const fetchWithRetries = async (url: string, retries = 3): Promise<string> => {
  for (let i = 0; i < retries; i++) {
    try {
      const { data } = await axios.get(url, {
        headers: {
          'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36',
        },
        timeout: 30000,
      })
      return data
    } catch (error) {
      console.warn(`Error fetching ${url} (Attempt ${i + 1}/${retries}): ${error}`)
      if (i === retries - 1) throw error
      await delay(1000)
    }
  }
  return ''
}

const parseNewsItem = ($: cheerio.CheerioAPI, element: Element, scrapedAt: number): NewsItem => {
  const $element = $(element) as cheerio.Cheerio<Element>
  const title = $element.find('h3.w357ui-margin-small-bottom.ma-title a').text().trim()
  const link = 'https://www.egerton.ac.ke' + ($element.find('h3.w357ui-margin-small-bottom.ma-title a').attr('href') || '')
  const imageTag = $element.find('.ma-image img')
  const imageUrl = imageTag.length ? 'https://www.egerton.ac.ke' + (imageTag.attr('src') || '') : null
  const date = $element.find('.ma-date time').text().trim()
  const intro = $element.find('.ma-introtext').text().trim()

  return { title, link, imageUrl, date, intro, scrapedAt }
}

export async function scrapeNews(
  pageStart = 0,
  pageCount = 5,
  onPageScraped?: (pageNews: NewsItem[]) => Promise<void>
): Promise<void> {
  const baseUrl = 'https://www.egerton.ac.ke/news';
  const maxPages = pageStart + pageCount;

  for (let currentPage = pageStart; currentPage < maxPages; currentPage++) {
    const url = `${baseUrl}?start=${25 * currentPage}`;
    console.log(`Fetching page ${currentPage + 1}: ${url}`);

    try {
      const data = await fetchWithRetries(url);
      const $ = cheerio.load(data);

      const newsArticles = $('.w357ui-grid-small.ma-article');
      if (newsArticles.length === 0) break;

      const pageNews: NewsItem[] = [];
      newsArticles.each((index, element) => {
        const scrapedAt = Date.now() - (currentPage * 25000) - (index * 1000);
        pageNews.push(parseNewsItem($, element as Element, scrapedAt));
      });

      if (onPageScraped) {
        await onPageScraped(pageNews); 
      }
    } catch (error) {
      console.error(`Error scraping page ${currentPage + 1}: ${error}`);
      break;
    }

    await delay(1000); 
  }
}


const parseDownloadItem = ($link: cheerio.Cheerio<Element>, scrapedAt: number): DownloadItem | null => {
  const title = $link.text().trim()
  const href = $link.attr('href')
  if (href) {
    const fullLink = 'https://www.egerton.ac.ke' + href.trim()
    const fileFormat = path.extname(href)

    return { title, link: fullLink, format: fileFormat, scrapedAt }
  }
  return null
}

export async function scrapeStudentDownloads(): Promise<DownloadItem[]> {
  const url = 'https://www.egerton.ac.ke/studentdownloads'
  const downloadItems: DownloadItem[] = []
  const scrapeStartTime = Date.now()

  try {
    const data = await fetchWithRetries(url)
    const $ = cheerio.load(data)

    $('ul.nav.menu.egerton-padding.mod-list').each((_, ul) => {
      $(ul).find('a').each((index, link) => {
        const $link = $(link) as cheerio.Cheerio<Element>
        const scrapedAt = scrapeStartTime + index * 1000
        const downloadItem = parseDownloadItem($link, scrapedAt)
        if (downloadItem) downloadItems.push(downloadItem)
      })
    })
  } catch (error) {
    console.error(`Error scraping downloads: ${error}`)
  }

  return downloadItems
}

