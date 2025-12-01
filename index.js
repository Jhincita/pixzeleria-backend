import express from "express";

const app = express();

app.get("/", (req, res) => {
  res.send("Hello, Pixzeleria!");
});

const port = process.env.PORT || 3000;

app.listen(port, () => {
  console.log(`http://localhost:${port}`);
});
