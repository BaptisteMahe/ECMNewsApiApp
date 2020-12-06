package fr.centrale.newsapiapp

class ArticlePreview {
    var title: String = ""
    var author: String = ""
    var date: String = ""
    var urlToImage: String? = ""

    constructor() {}

    constructor(title: String, author: String, date: String) {
        this.title = title
        this.author = author
        this.date = date
        this.urlToImage = urlToImage
    }
}