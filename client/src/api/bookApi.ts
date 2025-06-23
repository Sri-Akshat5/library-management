import type { Book, BookDto } from '../types/book';
import type { ApiResponse } from '../types/api';
import  api  from '../utils/api';

export const getBooks = async (): Promise<ApiResponse<Book[]>> => {
  return api.get('/api/books');
};

export const getBookById = async (id: number): Promise<ApiResponse<Book>> => {
  return api.get(`/api/books/${id}`);
};

export const createBook = async (book: BookDto): Promise<ApiResponse<Book>> => {
  return api.post('/api/books', book);
};

export const updateBook = async (id: number, book: BookDto): Promise<ApiResponse<Book>> => {
  return api.put(`/api/books/${id}`, book);
};

export const deleteBook = async (id: number): Promise<ApiResponse<string>> => {
  return api.delete(`/api/books/${id}`);
};

export const searchBooks = async (keyword: string): Promise<ApiResponse<Book[]>> => {
  return api.get('/api/books/search', { params: { keyword } });
};