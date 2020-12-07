package fr.centrale.newsapiapp

class ArticlePreview {
    var title: String = ""
    var author: String = ""
    var date: String = ""
    var sourceName: String = ""
    var description: String = ""
    var link: String = ""
    var urlToImage: String? = ""

    constructor() {}

    constructor(title: String,
                author: String,
                date: String,
                sourceName: String,
                description: String,
                link: String,
                urlToImage: String?) {
        this.title = title
        this.author = author
        this.date = date
        this.sourceName = sourceName
        this.description = description
        this.link = link
        this.urlToImage = urlToImage
    }
}