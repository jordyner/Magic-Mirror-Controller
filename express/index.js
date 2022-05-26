const express = require('express');
const app = express();
const PORT = 8088;

app.use(express.json({ limit: '50mb' }));
app.use(express.urlencoded({ extended: false }, { limit: '50mb' }));

var log = {};
const categoryLog = []
var items = []
var type_categories = []
var age_categories = []
var post = {}
var general = {}

app.listen(
    PORT,
    () => console.log('it alive on http://localhost:' + PORT)
)

app.post("/items", (req, res) => {
    const incoming = req.body;
    console.log(req.body);      // your JSON
    console.log(res.status);
    items = incoming;

    res.status(200).send(items);    // echo the result back
})

app.get("/items", (req, res) => {
    res.status(200).send(items);
})

app.post("/categories/age", (req, res) => {
    const incoming = req.body;
    console.log(req.body);      // your JSON
    console.log(res.status);
    age_categories = incoming;

    res.status(200).send(age_categories);    // echo the result back
})

app.get("/categories/age", (req, res) => {
    res.status(200).send(age_categories);
})

app.post("/categories/type", (req, res) => {
    const incoming = req.body;
    console.log(req.body);      // your JSON
    console.log(res.status);
    type_categories = incoming;

    res.status(200).send(type_categories);    // echo the result back
})

app.get("/categories/type", (req, res) => {
    res.status(200).send(type_categories);
})

app.post("/posts", (req, res) => {
    const incoming = req.body;
    console.log(req.body);      // your JSON
    console.log(res.status);
    post = incoming;

    res.status(200).send(post);    // echo the result back
})

app.get("/posts", (req, res) => {
    res.status(200).send(post);
})

app.post("/general", (req, res) => {
    const incoming = req.body;
    console.log(req.body);      // your JSON
    console.log(res.status);
    general = incoming;

    res.status(200).send(general);    // echo the result back
})

app.get("/general", (req, res) => {
    res.status(200).send(general);
})

