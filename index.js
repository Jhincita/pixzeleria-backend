import express from "express";

const app = express();

app.get("/", (req, res) => {
  res.send("Hello, Pixzeleria!");
});

const port = process.env.PORT || 8080;

app.listen(port, '0.0.0.0', () => {
  console.log(`Server running on port ${port}`);
});