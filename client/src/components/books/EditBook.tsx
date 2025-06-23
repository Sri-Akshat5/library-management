import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getBookById, updateBook } from '../../api/bookApi';
import type { Book } from '../../types/book';

const EditBook: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [book, setBook] = useState<Book>({
    bookId: 0,
    bookName: '',
    author: '',
    isbn: 0,
    totalCopies: 0,
    availableCopies: 0,
  });
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchBook = async () => {
      if (id) {
        const response = await getBookById(parseInt(id, 10));
        if (response.data) setBook(response.data);
        if (response.error) setError(response.error);
      }
    };
    fetchBook();
  }, [id]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setBook({
      ...book,
      [name]: name === 'bookName' || name === 'author' ? value : parseInt(value, 10),
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (book.bookId) {
      const response = await updateBook(book.bookId, book);
      if (response.data) {
        navigate('/books');
      }
      if (response.error) setError(response.error);
    }
  };

  return (
    <div className="max-w-lg mx-auto mt-10 bg-zinc-900 p-8 rounded shadow">
      <h2 className="text-2xl font-bold mb-6 text-white">Edit Book</h2>

      <form onSubmit={handleSubmit}>
        <div className="mb-4">
          <label className="block text-gray-50">Title</label>
          <input
            type="text"
            name="bookName"
            value={book.bookName}
            onChange={handleChange}
            className="w-full mt-1 p-2 border rounded bg-zinc-600 text-white border-zinc-900"
            required
          />
        </div>
        <div className="mb-4">
          <label className="block text-gray-50">Author</label>
          <input
            type="text"
            name="author"
            value={book.author}
            onChange={handleChange}
            className="w-full mt-1 p-2 border rounded bg-zinc-600 text-white border-zinc-900"
            required
          />
        </div>
        <div className="mb-4">
          <label className="block text-gray-50">ISBN</label>
          <input
            type="number"
            name="isbn"
            value={book.isbn}
            onChange={handleChange}
            className="w-full mt-1 p-2 border rounded bg-zinc-600 text-white border-zinc-900"
            required
          />
        </div>
        <div className="mb-6">
          <label className="block text-gray-50">Total Copies</label>
          <input
            type="number"
            min="1"
            name="totalCopies"
            value={book.totalCopies}
            onChange={handleChange}
            className="w-full mt-1 p-2 border rounded bg-zinc-600 text-white border-zinc-900"
            required
          />
        </div>
        <div className="mb-6">
          <label className="block text-gray-50">Available Copies</label>
          <input
            type="number"
            min="1"
            name="availableCopies"
            value={book.availableCopies}
            onChange={handleChange}
            className="w-full mt-1 p-2 border rounded bg-zinc-600 text-white border-zinc-900"
            required
          />
        </div>
        <button
          type="submit"
          className="bg-zinc-600 text-white px-4 py-2 rounded hover:bg-zinc-700"
        >
          Save Changes
        </button>
        <button
          type="button"
          onClick={() => navigate('/books')}
          className="ml-4 bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600"
        >
          Cancel
        </button>
      </form>

      {error && <p className="mt-4 text-red-500">{error}</p>}
    </div>
  );
};

export default EditBook;