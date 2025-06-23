import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { getBooks, searchBooks, deleteBook } from '../../api/bookApi';
import type { Book } from '../../types/book';
import { confirmAction } from '../../utils/helpers';
import { FaBook } from "react-icons/fa6";
import { FaEdit } from "react-icons/fa";
import { RiDeleteBin6Fill } from "react-icons/ri";


const BookList: React.FC = () => {
  const [books, setBooks] = useState<Book[]>([]);
  const [keyword, setKeyword] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
 

  useEffect(() => {
    fetchBooks();
  }, []);

  const fetchBooks = async () => {
    const response = await getBooks();
    if (response.data) setBooks(response.data);
    if (response.error) setError(response.error);
  };

  const handleSearch = async (e: React.FormEvent) => {
    e.preventDefault();
    const response = await searchBooks(keyword);
    if (response.data) setBooks(response.data);
    if (response.error) setError(response.error);
  };

  const handleDelete = async (id: number) => {
    if (confirmAction('Are you sure you want to delete this book?')) {
      const response = await deleteBook(id);
      if (response.data) {
        setSuccess('Book deleted successfully');
        fetchBooks();
      }
      if (response.error) setError(response.error);
    }
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex flex-col md:flex-row justify-between items-start md:items-center mb-8 gap-4">
        <div className="flex items-center">
          <div className="bg-zinc-600 p-3 rounded-lg shadow-md">
           <FaBook className =" text-white text-2xl"/>
          </div>
          <h1 className="ml-4 text-3xl font-bold text-zinc-800">Book Management</h1>
        </div>

        <div className="flex flex-col sm:flex-row gap-3 w-full md:w-auto">
          <form onSubmit={handleSearch} className="relative w-full">
            <input
              type="text"
              value={keyword}
              onChange={(e) => setKeyword(e.target.value)}
              placeholder="Search books"
              className="w-full pl-10 pr-4 py-2 rounded-lg  border bg-zinc-700 text-white border-zinc-900 "
            />
            
          </form>

          <Link
            to="/books/add"
            className="bg-zinc-600 hover:bg-zinc-700 text-white font-medium py-2 px-4 rounded-lg transition duration-200 flex items-center justify-center whitespace-nowrap"
          >
            Add Book
          </Link>
        </div>
      </div>

      <div className="bg-zinc-900 rounded-xl shadow-lg overflow-hidden border border-gray-200">
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-zinc-200">
            <thead className="bg-gradient-to-r from-zinc-600 to-zinc-800">
              <tr>
                <th className="px-6 py-4 text-left text-xs font-semibold text-white uppercase tracking-wider">ID</th>
                <th className="px-6 py-4 text-left text-xs font-semibold text-white uppercase tracking-wider">ISBN</th>
                <th className="px-6 py-4 text-left text-xs font-semibold text-white uppercase tracking-wider">Title</th>
                <th className="px-6 py-4 text-left text-xs font-semibold text-white uppercase tracking-wider">Author</th>
                <th className="px-6 py-4 text-left text-xs font-semibold text-white uppercase tracking-wider">Total</th>
                <th className="px-6 py-4 text-left text-xs font-semibold text-white uppercase tracking-wider">Available</th>
                <th className="px-6 py-4 text-left text-xs font-semibold text-white uppercase tracking-wider">Actions</th>
              </tr>
            </thead>
            <tbody className="bg-zinc-900 divide-y divide-zinc-200">
              {books.map((book) => (
                <tr key={book.bookId} className="hover:bg-zinc-500 transition duration-150">
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-white">{book.bookId}</td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-white">{book.isbn}</td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-white">{book.bookName}</td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-white">{book.author}</td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-white">
                    <span className="px-2 py-1 bg-zinc-100 text-zinc-800 rounded-full text-xs font-semibold">
                      {book.totalCopies}
                    </span>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-white">
                    <span className="px-2 py-1 bg-green-100 text-green-800 rounded-full text-xs font-semibold">
                      {book.availableCopies}
                    </span>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                    <div className="flex space-x-3">
                      <Link
                        to={`/books/edit/${book.bookId}`}
                        className="text-blue-600 hover:text-zinc-800 transition duration-150 cursor-pointer"
                        title="Edit"
                      >
                        <FaEdit />
                      </Link>
                      <button
                        onClick={() => handleDelete(book.bookId)}
                        className="text-red-600 hover:text-red-800 transition duration-150 cursor-pointer"
                        title="Delete"
                      >
                      <RiDeleteBin6Fill />

                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </div>

      {error && (
        <div className="mt-6 p-4 bg-red-50 border-l-4 border-red-500 text-red-700">
          <div className="flex items-center">
            <i className="fas fa-exclamation-circle mr-3"></i>
            <span>{error}</span>
          </div>
        </div>
      )}

      {success && (
        <div className="mt-6 p-4 bg-green-50 border-l-4 border-green-500 text-green-700">
          <div className="flex items-center">
            <i className="fas fa-check-circle mr-3"></i>
            <span>{success}</span>
          </div>
        </div>
      )}
    </div>
  );
};

export default BookList;