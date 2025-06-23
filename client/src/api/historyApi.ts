import type { History, BorrowFormData, BorrowRequest } from '../types/history';
import type { ApiResponse } from '../types/api';
import  api  from '../utils/api';

export const getHistory = async (): Promise<ApiResponse<History[]>> => {
  return api.get('/api/history');
};

export const getBorrowFormData = async (): Promise<ApiResponse<BorrowFormData>> => {
  return api.get('/api/history/borrow/form-data');
};

export const borrowBook = async (request: BorrowRequest): Promise<ApiResponse<string>> => {
  return api.post('/api/history/borrow', null, { params: request });
};

export const returnBook = async (id: number): Promise<ApiResponse<string>> => {
  return api.put(`/api/history/return/${id}`);
};

export const searchHistory = async (keyword: string): Promise<ApiResponse<History[]>> => {
  return api.get('/api/history/search', { params: { keyword } });
};