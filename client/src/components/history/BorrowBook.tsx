import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getBorrowFormData, borrowBook } from '../../api/historyApi';
import type { BorrowFormData, BorrowRequest } from '../../types/history';

const BorrowBook: React.FC = () => {
  const [formData, setFormData] = useState<BorrowFormData>({
    students: [],
    books: [],
  });
  const [request, setRequest] = useState<BorrowRequest>({
    studentId: 0,
    bookId: 0,
  });
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchFormData = async () => {
      const response = await getBorrowFormData();
      if (response.data) setFormData(response.data);
      if (response.error) setError(response.error);
    };
    fetchFormData();
  }, []);

  const handleChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const { name, value } = e.target;
    setRequest({
      ...request,
      [name]: parseInt(value, 10),
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const response = await borrowBook(request);
    if (response.data) {
      navigate('/history');
    }
    if (response.error) setError(response.error);
  };

  return (
    <div className="max-w-lg mx-auto mt-8">
      <h2 className="text-2xl font-semibold mb-6 text-zinc-800">Borrow Book</h2>
      <form onSubmit={handleSubmit} className="space-y-4 bg-zinc-900 p-6 rounded-lg shadow-md">
        <div>
          <label className="block text-white font-medium mb-2">Select Student</label>
          <select
            name="studentId"
            value={request.studentId}
            onChange={handleChange}
            required
            className="w-full p-2 border rounded-md  bg-zinc-700 text-white border-zinc-900"
          >
            <option value="" disabled >
              Choose a student
            </option>
            {formData.students.map((s) => (
              <option key={s.studentId} value={s.studentId}>
                {s.firstName} {s.lastName}
              </option>
            ))}
          </select>
        </div>

        <div>
          <label className="block text-white font-medium mb-2">Select Book</label>
          <select
            name="bookId"
            value={request.bookId}
            onChange={handleChange}
            required
            className="w-full p-2 border  rounded-md focus:ring-2 bg-zinc-700 text-white border-zinc-900"
          >
            <option value="" disabled selected>
              Choose a book
            </option>
            {formData.books.map((b) => (
              <option key={b.bookId} value={b.bookId}>
                {b.bookName} by {b.author}
              </option>
            ))}
          </select>
        </div>

        <div className="flex items-center pt-4">
          <button
            type="submit"
            className="bg-zinc-600 text-white px-4 py-2 rounded-md hover:bg-zinc-700 focus:outline-none focus:ring-2 focus:ring-zinc-500 focus:ring-offset-2"
          >
            Borrow Book
          </button>
          <button
            type="button"
            onClick={() => navigate('/history')}
            className="ml-4 text-gray-500 hover:text-gray-800 hover:underline"
          >
            Back to History
          </button>
        </div>
      </form>

      {error && <p className="mt-4 text-red-500">{error}</p>}
    </div>
  );
};

export default BorrowBook;