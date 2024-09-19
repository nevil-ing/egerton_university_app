from bs4 import BeautifulSoup
from urllib.request import urlopen
from urllib.parse import urljoin

url = 'https://www.egerton.ac.ke'
page = urlopen(url)
html = page.read().decode('utf-8')
soup = BeautifulSoup(html, 'html.parser')


info_section = soup.find('li', class_='sp-menu-item sp-has-child menu-justify')
print(info_section)
if info_section:

    info_div = soup.find('div', class_='sp-dropdown sp-dropdown-main sp-dropdown-mega sp-menu-full container')
    print(info_div)
    if info_div:
        info_dropdown = soup.find('div', class_='sp-dropdown-inner')
        print(info_dropdown)
        if info_dropdown:
            info_div_row = soup.find('div', class_='row')
            for div in info_div_row(soup.findAll('div', class_='col-sm-3')):
                div_item = div[2]
                if div_item:
                    student_section = soup.find('li', class_='sp-menu-item')
                    if student_section:
                        student_notice_link = soup.find('a', class_='sp-menu-item')
                        student_notice_url = urljoin(url, student_notice_link['href'])
                        print(student_notice_url)

'''
    if student_notice_link:

        student_notice_url = urljoin(url, student_notice_link['href'])
        student_notice_page = urlopen(student_notice_url)
        student_notice_html = student_notice_page.read().decode('utf-8')
        student_notice_soup = BeautifulSoup(student_notice_html, 'html.parser')

        student_notice_articles = student_notice_soup.find('ul', class_='allmode-items')
        if student_notice_articles:
            student_notice_article = student_notice_articles.findAll('li', class_='allmode-item')
            for li in student_notice_article:

                image = li.find('div', class_='allmode-img')
                if image:
                    image_link = image.find('a')
                    image_url = image_link['href']
                    print('Image URL:', image_url)

                date = li.find('div', class_='allmode-date').get_text(strip=True)
                print('Date:', date)

                title = li.find('div', class_='allmode-title').get_text(strip=True)
                print('Title:', title)

                text = li.find('div', class_='allmode-text').get_text(strip=True)
                print('Text:', text)

                readmore = li.find('div', class_='allmode-readmore').get_text(strip=True)
                print('Read More:', readmore)
    else:
        print("Notice Board link not found.")
else:
    print("Students section not found.")
'''