import axios from 'axios';

const API_BASE = process.env.REACT_APP_API_URL || 'http://localhost:8085';

const api = axios.create({
  baseURL: API_BASE,
  headers: { 'Content-Type': 'application/json' },
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

api.interceptors.response.use(
  (res) => res,
  (err) => {
    if (err.response?.status === 401) {
      localStorage.clear();
      window.location.href = '/login';
    }
    return Promise.reject(err);
  }
);

export const login = async (username: string, password: string) => {
  const res = await api.post('/auth/login', { username, password });
  return res.data;
};

export const onboardEmployee = async (data: {
  name: string;
  email: string;
  department: string;
  salary: number;
}) => {
  const res = await api.post('/api/onboard-employee', data);
  return res.data;
};

export const getEmployees = async () => {
  const res = await api.get('/employees');
  return res.data;
};

export default api;
