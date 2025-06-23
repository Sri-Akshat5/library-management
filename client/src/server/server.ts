import express from 'express';
import cors from 'cors';

const app = express();
const PORT = 3000;

app.use(cors()); 

app.get('/api/data', (req, res) => {
  res.json({ message: 'Hello World' });
});

app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`);
});
