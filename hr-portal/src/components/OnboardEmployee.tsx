import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Container, TextField, Button, Typography, Box, Alert, CircularProgress, Paper, Table, TableBody, TableCell, TableRow
} from '@mui/material';
import { onboardEmployee } from '../services/api';

const OnboardEmployee: React.FC = () => {
  const [form, setForm] = useState({ name: '', email: '', department: '', salary: '' });
  const [result, setResult] = useState<any>(null);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
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
    } catch (err: any) {
      setError(err.response?.data?.message || 'Onboarding failed');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container maxWidth="md">
      <Box sx={{ mt: 4 }}>
        <Typography variant="h4" gutterBottom>Onboard New Employee</Typography>
        <Button onClick={() => navigate('/dashboard')} sx={{ mb: 2 }}>Back to Dashboard</Button>
      </Box>
      <Paper sx={{ p: 3 }}>
        <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2 }}>
          <TextField label="Name" value={form.name} onChange={handleChange('name')} />
          <TextField label="Email" type="email" value={form.email} onChange={handleChange('email')} />
          <TextField label="Department" value={form.department} onChange={handleChange('department')} />
          <TextField label="Salary" type="number" value={form.salary} onChange={handleChange('salary')} />
          <Button variant="contained" onClick={handleSubmit} disabled={loading}>
            {loading ? <CircularProgress size={24} /> : 'Create Employee'}
          </Button>
        </Box>
      </Paper>
      {error && <Alert severity="error" sx={{ mt: 2 }}>{error}</Alert>}
      {result && (
        <Paper sx={{ p: 3, mt: 2 }}>
          <Typography variant="h6">Onboarding Result</Typography>
          <Table>
            <TableBody>
              {Object.entries(result).map(([key, val]) => (
                <TableRow key={key}>
                  <TableCell><strong>{key}</strong></TableCell>
                  <TableCell>{String(val)}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </Paper>
      )}
    </Container>
  );
};

export default OnboardEmployee;
