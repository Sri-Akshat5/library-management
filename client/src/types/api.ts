export interface DashboardData {
  bookCount: number;
  studentCount: number;
  borrowedCount: number;
}

// export interface ApiResponse<T> {
//   data?: T;
//   // eslint-disable-next-line @typescript-eslint/no-explicit-any
//   error?: any;
//   success?: boolean;
// }

export interface ApiResponse<T> {
  success: boolean;
  data?: T;
  error?: string;
}
