import api from '../utils/api'; // assuming baseURL is set to http://localhost:8080
import type { DashboardData, ApiResponse } from '../types/api';


export const getDashboardData = async (): Promise<ApiResponse<DashboardData>> => {
  try {
    const [booksRes, studentsRes, historyRes] = await Promise.all([
      api.get('api/books'),
          api.get('api/students'),
          api.get('api/history'),
    ]);

    const borrowedCount = historyRes.data.filter((h: { flag: string; }) => h.flag === 'BORROWED').length;

    return {
      success: true,
      data: {
        bookCount: booksRes.data.length,
        studentCount: studentsRes.data.length,
        borrowedCount,
      },
    };
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    return {
      success: false,
      error: error.error || 'Failed to fetch dashboard data',
    };
  }
};
