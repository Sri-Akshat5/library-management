import React from 'react';
import Navbar from './NavBar';

interface LayoutProps {
  children: React.ReactNode;
}

const Layout: React.FC<LayoutProps> = ({ children }) => {
  return (
    <div className="bg-zinc-300 min-h-screen font-sans">
      <Navbar />
      <main className="container mx-auto px-4 py-8">{children}</main>
    </div>
  );
};

export default Layout;