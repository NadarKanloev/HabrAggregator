import requests
from bs4 import BeautifulSoup
import os
import numpy as np
import pandas as pd
from tqdm import tqdm
import multiprocessing as mp

headers = {
    "Accept": "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8",
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)"
                  " Chrome/106.0.0.0 Safari/537.36"
}


def save_hubs_pages_data(page_url):
    if not os.path.isdir("hubs_pages"):
        os.mkdir("hubs_pages")
        for i in range(1, 12):
            req = requests.get(f"{page_url}page{str(i)}", headers=headers)
            with open(f"./hubs_pages/hubs_page_{i}.html", "w", encoding="utf-8") as file:
                file.write(req.text)


def save_hub_data(link, hub_dir):
    folder_name = f"hubs\\{hub_dir}"
    if not os.path.isdir(folder_name):
        os.makedirs(folder_name)
        for i in range(1, 11):
            req = requests.get(f"{link}page{str(i)}", headers=headers)
            with open(f"./{folder_name}/page_{i}.html", "w", encoding="utf-8") as file:
                file.write(req.text)


def parse_hubs_pages():
    hubs_arr = np.empty([0, 4])
    for i in range(1, 12):
        with open(f"./hubs_pages/hubs_page_{i}.html", encoding="utf-8") as file:
            src = file.read()
        soup = BeautifulSoup(src, "lxml")
        hubs = soup.findAll("div", class_="tm-hub__info")
        for hub in tqdm(hubs):
            name = hub.find("a", class_="tm-hub__title")
            hub_name = name.text.lower().strip().replace(" ", "_")
            link = name['href']
            start_substr = link.rfind('/', 0, -1)
            hub_dir = link[start_substr + 1:-1].lower().strip()
            description = hub.find("div", class_="tm-hub__description")
            hub_link = f"https://habr.com{link}"
            save_hub_data(hub_link, hub_dir)
            hubs_arr = np.vstack((hubs_arr, np.array([hub_dir, hub_name, description.text, hub_link], dtype=object)))
    hubs_columns = ['hub_id', 'hub_name', 'hub_description', 'hub_link']
    hubs_df = pd.DataFrame(hubs_arr, columns=hubs_columns)
    if not os.path.isdir("data"):
        os.mkdir("data")
    hubs_df.to_csv("data\\hubs.csv", index=False)


def flatten(arr):
    return [item for sublist in arr for item in sublist]


def end_processing(b):
    print(f"hub {b} обработан")


def get_flows():
    flows = dict()
    for root, dirs, files in os.walk("./flows"):
        for directory in dirs:
            hub_df = pd.read_csv(f"{root}/{directory}/hubs.csv")
            hub_df['hub_id'].apply(lambda x: flows.update({x: directory}))
    return flows


def parse_hub(hub_dir, flow):
    all_articles = []
    for i in range(1, 11):
        with open(f"./hubs/{hub_dir}/page_{i}.html", encoding="utf-8") as file:
            src = file.read()
        soup = BeautifulSoup(src, "lxml")
        articles = soup.findAll("article")
        all_articles.append(articles)
    all_articles = flatten(all_articles)
    if len(all_articles) == 200:
        arts_arr = np.empty([0, 11])
        for art in all_articles:
            author = art.find("a", class_="tm-user-info__username")
            company_link = art.find("a", class_="tm-article-snippet__hubs-item-link")
            rating = art.find("span", {"data-test-id": "votes-meter-value"})
            views = art.find("span", class_="tm-icon-counter__value")
            bookmarks = art.find("span", class_="bookmarks-button__counter")
            comments = art.find("span", class_="tm-article-comments-counter-link__value")
            pub_datetime = art.find("span", class_="tm-article-snippet__datetime-published")
            if views is None:
                continue
            if pub_datetime is None:
                pub_datetime = art.find("time")
                pub_datetime = pub_datetime['datetime']
            else:
                pub_datetime = pub_datetime.findNext()['datetime']
            if company_link is None:
                company_link = art.find("a")['href']
            elif company_link.findNext().text.startswith("Блог компании"):
                company_link = company_link['href']
            else:
                company_link = "without_company"
            if author is None:
                author = ""
            else:
                author = author.text.strip()
            if rating.text.strip() == "0":
                rating_des = ""
            else:
                rating_des = rating["title"]
            arts_arr = np.vstack((arts_arr, np.array([
                hub_dir, flow, art['id'], author,
                pub_datetime, rating.text.strip(), rating_des, views.text.strip(), bookmarks.text.strip(),
                comments.text.strip(), company_link
            ], dtype=object)))
        arts_columns = ['hub_id', 'flow', 'article_id', 'author', 'pub_datetime', 'rating', 'rating_des', 'views',
                        'bookmarks', 'comments', 'company_link']
        arts_df = pd.DataFrame(arts_arr, columns=arts_columns)
        if not os.path.isdir(f"data\\{hub_dir}"):
            os.makedirs(f"data\\{hub_dir}")
        arts_df.to_csv(f"data\\{hub_dir}\\articles.csv", index=False)
    return hub_dir


def parse_all_posts():
    with mp.Pool() as pool:
        flows = get_flows()
        for root, dirs, files in os.walk("./hubs"):
            for directory in dirs:
                flow = flows.get(directory)
                pool.apply_async(parse_hub, (directory, flow, ), callback=end_processing)
        pool.close()
        pool.join()


def load_df(path, name):
    posts = pd.DataFrame()
    for root, dirs, files in os.walk(path):
        for directory in dirs:
            hub_df = pd.read_csv(f"{root}{directory}/{name}")
            posts = pd.concat([posts, hub_df])
    return posts


def views_text_to_number(r):
    if r[-1] == 'K':
        return int(float(r[:-1]) * 1000)
    return r


def is_company_post(r):
    return not r == "without_company"


def save_all_flows_data():
    flows = {"develop": 7, "admin": 1, "design": 1, "management": 1, "marketing": 1, "popsci": 2}
    for flow, pages in flows.items():
        if not os.path.isdir(f"flows\\{flow}"):
            hubs = []
            os.makedirs(f"flows\\{flow}")
            for i in range(1, pages+1):
                req = requests.get(f"https://habr.com/ru/flows/{flow}/hubs/page{i}/", headers=headers)
                page_hubs = parse_flow_page(req.text)
                hubs.append(page_hubs)
                with open(f"./flows/{flow}/flow_page_{i}.html", "w", encoding="utf-8") as file:
                    file.write(req.text)
            hubs_columns = ['hub_id']
            arts_df = pd.DataFrame(flatten(hubs), columns=hubs_columns)
            if not os.path.isdir(f"./flows/{flow}"):
                os.makedirs(f"./flows/{flow}")
            arts_df.to_csv(f"flows\\{flow}\\hubs.csv", index=False)


def parse_flow_page(text):
    soup = BeautifulSoup(text, "lxml")
    hubs = soup.findAll("div", class_="tm-hub")
    res = []
    for hub in hubs:
        name = hub.find("a", class_="tm-hub__title")
        hub_name = name.text.lower().strip().replace(" ", "_")
        res.append(hub_name)
    return res


def preprocess():
    posts = load_df("data/", "articles.csv")
    posts["hub_count"] = posts.groupby("article_id")["hub_id"].transform('count')
    posts.drop({"hub_id"}, axis=1, inplace=True)
    posts.drop_duplicates(subset="article_id", inplace=True)
    posts['views'] = posts['views'].apply(views_text_to_number)
    posts['is_company_post'] = posts['company_link'].apply(is_company_post)
    posts.drop({"company_link"}, axis=1, inplace=True)
    posts.to_csv("posts.csv", index=False)


if __name__ == "__main__":
    save_hubs_pages_data("https://habr.com/ru/hubs/")
    save_all_flows_data()
    parse_hubs_pages()
    parse_all_posts()
    preprocess()
