import express from "express";

const app = express();

app.get("/", (req, res) => {
  res.send("Hello, Pixzeleria!");
});

// Keep this line to get the port from Railway or default to 8080 locally
const port = process.env.PORT || 8080;

app.listen(port, () => {
  console.log(`Server running on port ${port}`);
});
