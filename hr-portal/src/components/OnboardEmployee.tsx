import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Container, TextField, Button, Typography, Box, Alert, CircularProgress,
  Paper, Table, TableBody, TableCell, TableRow, Snackbar, Fade
} from '@mui/material';
import { onboardEmployee } from '../services/api';

const OnboardEmployee: React.FC = () => {
  const [form, setForm] = useState({ name: '', email: '', department: '', salary: '' });
  const [result, setResult] = useState<any>(null);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [toastOpen, setToastOpen] = useState(false);
  const navigate = useNavigate();

  const handleChange = (field: string) => (e: React.ChangeEvent<HTMLInputElement>) => {
    setForm({ ...form, [field]: e.target.value });
  };

  const handleSubmit = async () => {
    setLoading(true);
    setError('');
    setResult(null);
    try {
      const res = await onboardEmployee({
        ...form,
        salary: Number(form.salary),
      });
      setResult(res);
      setToastOpen(true);
    } catch (err: any) {
      setError(err.response?.data?.message || err.response?.data?.reason || 'Onboarding failed');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container maxWidth="md">
      <Box sx={{ mt: 4 }}>
        <Typography variant="h4" fontWeight={600} gutterBottom>Onboard New Employee</Typography>
        <Button onClick={() => navigate('/dashboard')} sx={{ mb: 2 }}>← Back to Dashboard</Button>
      </Box>
      <Paper sx={{ p: 3 }}>
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2.5 }}>
          <TextField label="Full Name" value={form.name} onChange={handleChange('name')} required />
          <TextField label="Email" type="email" value={form.email} onChange={handleChange('email')} required />
          <TextField label="Department" value={form.department} onChange={handleChange('department')} required />
          <TextField label="Salary" type="number" value={form.salary} onChange={handleChange('salary')} required />
          <Button
            variant="contained"
            size="large"
            onClick={handleSubmit}
            disabled={loading || !form.name || !form.email || !form.department || !form.salary}
            sx={{ py: 1.5 }}
          >
            {loading ? <CircularProgress size={24} color="inherit" /> : 'Create Employee'}
          </Button>
        </Box>
      </Paper>

      {error && (
        <Fade in={!!error}>
          <Alert severity="error" sx={{ mt: 2 }}>{error}</Alert>
        </Fade>
      )}

      {result && (
        <Fade in={!!result}>
          <Paper sx={{ p: 3, mt: 2, border: '2px solid #4caf50' }}>
            <Box sx={{ display: 'flex', alignItems: 'center', gap: 1, mb: 2 }}>
              <svg viewBox="0 0 24 24" fill="#4caf50" width="32" height="32"><path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm-2 15l-5-5 1.41-1.41L10 14.17l7.59-7.59L19 8l-9 9z"/></svg>
              <Typography variant="h6" color="success.main">Onboarding Successful</Typography>
            </Box>
            <Table>
              <TableBody>
                {Object.entries(result).map(([key, val]) => (
                  <TableRow key={key}>
                    <TableCell sx={{ fontWeight: 600, width: 200 }}>{key}</TableCell>
                    <TableCell>{String(val)}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </Paper>
        </Fade>
      )}

      <Snackbar
        open={toastOpen}
        autoHideDuration={4000}
        onClose={() => setToastOpen(false)}
        message="Employee onboarded successfully!"
        anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }}
      />
    </Container>
  );
};

export default OnboardEmployee;
