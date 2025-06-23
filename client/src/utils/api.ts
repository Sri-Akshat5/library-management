import axios from 'axios';


const api = axios.create({
  baseURL: 'http://localhost:8080', // Update with your backend URL
  headers: {
    'Content-Type': 'application/json',
  },
});

// Response interceptor to handle API responses
// api/index.ts
api.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    const errMsg =
      error?.response?.data?.error || 'Something went wrong. Please try again.';
    return Promise.reject({ error: errMsg });
  }
);


export default api;
