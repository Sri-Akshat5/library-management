import type { Book } from "./book";
import type { Student } from "./student";

export interface History {
  historyId: number;
  studentId: number;
  bookId: number;
  firstName: string;
  bookName: string;
  issueDate: string;
  returnDate: string | null;
  flag: 'BORROWED' | 'RETURNED';
}

export interface BorrowFormData {
  students: Student[];
  books: Book[];
}

export interface BorrowRequest {
  studentId: number;
  bookId: number;
}