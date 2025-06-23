import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { getHistory, searchHistory, returnBook } from '../../api/historyApi';
import type { History } from '../../types/history';
import { formatDate } from '../../utils/helpers';
import { FaHistory } from "react-icons/fa";
import { FaUndo } from "react-icons/fa";



const HistoryList: React.FC = () => {
  const [histories, setHistories] = useState<History[]>([]);
  const [keyword, setKeyword] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  

  useEffect(() => {
    fetchHistory();
  }, []);

  const fetchHistory = async () => {
    const response = await getHistory();
    if (response.data) setHistories(response.data);
    if (response.error) setError(response.error);
  };

  const handleSearch = async (e: React.FormEvent) => {
    e.preventDefault();
    const response = await searchHistory(keyword);
    if (response.data) setHistories(response.data);
    if (response.error) setError(response.error);
  };

  const handleReturn = async (id: number) => {
    const response = await returnBook(id);
    if (response.data) {
      setSuccess('Book returned successfully');
      fetchHistory();
    }
    if (response.error) setError(response.error);
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="flex flex-col md:flex-row justify-between items-start md:items-center mb-8 gap-4">
        <div className="flex items-center">
          <div className="bg-zinc-600 p-3 rounded-lg shadow-md">
           <FaHistory className =" text-white text-2xl"/>
          </div>
          <h2 className="ml-4 text-3xl font-bold text-gray-800">Borrow History</h2>
        </div>

        <div className="flex flex-col sm:flex-row gap-3 w-full md:w-auto">
          <form onSubmit={handleSearch} className="relative w-full">
            <input
              type="text"
              value={keyword}
              onChange={(e) => setKeyword(e.target.value)}
              placeholder="Search by student name"
              className="w-full pl-10 pr-4 py-2 rounded-lg  border bg-zinc-700 text-white border-zinc-900 "
            />
          </form>

          <Link
            to="/history/borrow"
            className="bg-zinc-600 hover:bg-zinc-700 text-white font-medium py-2 px-4 rounded-lg transition duration-200 flex items-center justify-center whitespace-nowrap"
          >
            <i className="fas fa-plus mr-2"></i> New Borrow
          </Link>
        </div>
      </div>

      <div className="bg-zinc-900 rounded-xl shadow-lg overflow-hidden border border-zinc-200">
        <div className="overflow-x-auto">
          <table className="min-w-full divide-y divide-zinc-200">
            <thead className="bg-gradient-to-r from-zinc-600 to-zinc-800">
              <tr>
                <th className="px-6 py-4 text-left text-xs font-semibold text-white uppercase tracking-wider">History ID</th>
                <th className="px-6 py-4 text-left text-xs font-semibold text-white uppercase tracking-wider">Student Name</th>
                <th className="px-6 py-4 text-left text-xs font-semibold text-white uppercase tracking-wider">Book Name</th>
                <th className="px-6 py-4 text-left text-xs font-semibold text-white uppercase tracking-wider">Issue Date</th>
                <th className="px-6 py-4 text-left text-xs font-semibold text-white uppercase tracking-wider">Return Date</th>
                <th className="px-6 py-4 text-left text-xs font-semibold text-white uppercase tracking-wider">Status</th>
                <th className="px-6 py-4 text-left text-xs font-semibold text-white uppercase tracking-wider">Actions</th>
              </tr>
            </thead>
            <tbody className="bg-zinc-900 divide-y divide-zinc-200">
              {histories.map((h) => (
                <tr key={h.historyId} className="hover:bg-zinc-500 transition duration-150">
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-white">{h.historyId}</td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-white">{h.firstName}</td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-white">{h.bookName}</td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-white">{formatDate(h.issueDate)}</td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm text-white">{formatDate(h.returnDate)}</td>
                  <td className="px-6 py-4 whitespace-nowrap">
                    <span
                      className={`inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium ${
                        h.flag === 'BORROWED'
                          ? 'bg-red-100 text-red-800'
                          : 'bg-green-100 text-green-800'
                      }`}
                    >
                      {h.flag}
                    </span>
                  </td>
                  <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                    <div className="flex space-x-3">
                      {h.flag === 'BORROWED' && (
                        <button
                          onClick={() => handleReturn(h.historyId)}
                          className="text-blue-600 hover:text-blue-800 transition duration-150"
                          title="Return Book"
                        >
                         <FaUndo />

                        </button>
                      )}
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

export default HistoryList;