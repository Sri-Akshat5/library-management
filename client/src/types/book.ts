export interface Book {
  bookId: number;
  bookName: string;
  author: string;
  isbn: number;
  totalCopies: number;
  availableCopies: number;
}

export interface BookDto extends Omit<Book, 'bookId'> {
  bookId?: number;
}