import { NextResponse } from 'next/server';
import { db, messaging } from '@/lib/firebase-admin';
import { scrapeNews, scrapeStudentDownloads } from '@/lib/scraper';

export const config = {
  maxDuration: 300, 
};

async function getLatestNews() {
  const newsSnapshot = await db.collection('news')
    .orderBy('scrapedAt', 'desc')
    .limit(1)
    .get();

  if (newsSnapshot.empty) {
    return null;
  }

  return newsSnapshot.docs[0].data();
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
async function sendNotification(latestNews: any) {
  const truncatedIntro = latestNews.intro.length > 150 
    ? latestNews.intro.substring(0, 150) + '...' 
    : latestNews.intro;

  const message = {
    data: {
      Title: latestNews.title,
      Intro: truncatedIntro,
      Image_url: latestNews.imageUrl || '',
      Link: latestNews.link,
      MessageId: Date.now().toString(),
    },
    topic: 'notify',
  };

  try {
    const response = await messaging.send(message);
    console.log('Successfully sent message:', response);
  } catch (error) {
    console.error('Error sending message:', error);
  }
}

export async function POST() {
  try {
    if (!db) {
      throw new Error('Firestore is not initialized');
    }

    // Scrape and save news
    const newsCollection = db.collection('news');
    await scrapeNews(0, 5, async (pageNews) => {
      for (const item of pageNews) {
        const existingNews = await newsCollection.where('link', '==', item.link).get();

        if (existingNews.empty) {
          await newsCollection.add({
            ...item,
            timestamp: new Date(item.date),
            scrapedAt: new Date(item.scrapedAt),
          });
        }
      }
    });

    // Scrape and save student downloads
    const downloadsCollection = db.collection('studentDownloads');
    const downloadItems = await scrapeStudentDownloads(); 
    for (const item of downloadItems) {
      const existingDownload = await downloadsCollection.where('link', '==', item.link).get();

      if (existingDownload.empty) {
        await downloadsCollection.add({
          ...item,
          timestamp: new Date(),
          scrapedAt: new Date(item.scrapedAt),
        });
      }
    }

    // Get the latest news and send notification
    const latestNews = await getLatestNews();
    if (latestNews) {
      await sendNotification(latestNews);
    }

    return NextResponse.json({
      message: 'Scraping, saving, and notification sending completed successfully',
      newsCount: downloadItems.length,
      downloadsCount: downloadItems.length,
    });
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    console.error('Error during scraping or notification sending:', error.stack);
    return NextResponse.json(
      { error: 'error occurred during scraping or notification sending' },
      { status: 500 }
    );
  }
}

