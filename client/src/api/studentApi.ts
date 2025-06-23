import type { Student, StudentDto } from '../types/student';
import type { ApiResponse } from '../types/api';
import  api  from '../utils/api';

export const getStudents = async (): Promise<ApiResponse<Student[]>> => {
  return api.get('/api/students');
};

export const getStudentById = async (id: number): Promise<ApiResponse<Student>> => {
  return api.get(`/api/students/${id}`);
};

export const createStudent = async (student: StudentDto): Promise<ApiResponse<Student>> => {
  return api.post('/api/students', student);
};

export const updateStudent = async (id: number, student: StudentDto): Promise<ApiResponse<Student>> => {
  return api.put(`/api/students/${id}`, student);
};

export const deleteStudent = async (id: number): Promise<ApiResponse<string>> => {
  return api.delete(`/api/students/${id}`);
};

export const searchStudents = async (keyword: string): Promise<ApiResponse<Student[]>> => {
  return api.get('/api/students/search', { params: { keyword } });
};