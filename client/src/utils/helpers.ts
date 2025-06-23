export const formatDate = (dateString: string | null): string => {
  if (!dateString) return '---';
  const date = new Date(dateString);
  return date.toISOString().split('T')[0];
};

export const confirmAction = (message: string): boolean => {
  return window.confirm(message);
};