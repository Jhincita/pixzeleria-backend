import express from "express";
import cors from "cors";

const app = express();

// Allow your frontend origin
app.use(cors({
  origin: "https://pixzeleria-full-production.up.railway.app"
}));

app.get("/", (req, res) => {
  res.send("Hello, Pixzeleria!");
});

// Example API route
app.get("/api/pizzas", (req, res) => {
  res.json([
    { id: 1, name: "Margherita" },
    { id: 2, name: "Pepperoni" }
  ]);
});

const port = process.env.PORT || 8080;
app.listen(port, () => {
  console.log(`Server running on port ${port}`);
});
