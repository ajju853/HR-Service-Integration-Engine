import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, Typography, Button, Box, Paper } from '@mui/material';

const Dashboard: React.FC = () => {
  const navigate = useNavigate();
  const role = localStorage.getItem('role');

  return (
    <Container>
      <Box sx={{ mt: 4, display: 'flex', justifyContent: 'space-between' }}>
        <Typography variant="h4">HR Dashboard</Typography>
        <Button color="secondary" onClick={() => { localStorage.clear(); navigate('/login'); }}>Logout</Button>
      </Box>
      <Paper sx={{ p: 3, mt: 3 }}>
        <Typography variant="h6">Welcome, {role}</Typography>
        <Button variant="contained" sx={{ mt: 2 }} onClick={() => navigate('/onboard')}>
          Onboard New Employee
        </Button>
      </Paper>
    </Container>
  );
};

export default Dashboard;
