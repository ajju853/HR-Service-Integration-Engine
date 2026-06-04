import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, Typography, Button, Box, Paper, Grid, Card, CardContent, CardActions } from '@mui/material';


const Dashboard: React.FC = () => {
  const navigate = useNavigate();
  const role = localStorage.getItem('role');

  const handleLogout = () => {
    localStorage.clear();
    navigate('/login');
  };

  return (
    <Container maxWidth="md">
      <Box sx={{ mt: 4, display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
        <Typography variant="h4" fontWeight={600}>HR Dashboard</Typography>
        <Button variant="outlined" color="error" onClick={handleLogout}>Logout</Button>
      </Box>
      <Paper sx={{ p: 2, mt: 2, mb: 3, bgcolor: '#f5f5f5' }}>
        <Typography variant="subtitle1">Logged in as <strong>{role}</strong></Typography>
      </Paper>
      <Grid container spacing={3}>
        <Grid item xs={12} sm={6}>
          <Card sx={{ height: '100%' }}>
            <CardContent>
              <svg viewBox="0 0 24 24" fill="#1976d2" width="48" height="48" style={{marginBottom: 8}}><path d="M15 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm-9-2V7H4v3H1v2h3v3h2v-3h3v-2H6zm9 4c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/></svg>
              <Typography variant="h5">Onboard Employee</Typography>
              <Typography variant="body2" color="text.secondary">
                Create a new employee record with payroll, attendance, and email notification.
              </Typography>
            </CardContent>
            <CardActions>
              <Button size="large" variant="contained" fullWidth onClick={() => navigate('/onboard')}>
                Get Started
              </Button>
            </CardActions>
          </Card>
        </Grid>
        <Grid item xs={12} sm={6}>
          <Card sx={{ height: '100%' }}>
            <CardContent>
              <svg viewBox="0 0 24 24" fill="#dc004e" width="48" height="48" style={{marginBottom: 8}}><path d="M16 11c1.66 0 2.99-1.34 2.99-3S17.66 5 16 5c-1.66 0-3 1.34-3 3s1.34 3 3 3zm-8 0c1.66 0 2.99-1.34 2.99-3S9.66 5 8 5C6.34 5 5 6.34 5 8s1.34 3 3 3zm0 2c-2.33 0-7 1.17-7 3.5V19h14v-2.5c0-2.33-4.67-3.5-7-3.5zm8 0c-.29 0-.62.02-.97.05 1.16.84 1.97 1.97 1.97 3.45V19h6v-2.5c0-2.33-4.67-3.5-7-3.5z"/></svg>
              <Typography variant="h5">View Employees</Typography>
              <Typography variant="body2" color="text.secondary">
                Browse all employees with their payroll and attendance status.
              </Typography>
            </CardContent>
            <CardActions>
              <Button size="large" variant="outlined" fullWidth onClick={() => navigate('/employees')}>
                View All
              </Button>
            </CardActions>
          </Card>
        </Grid>
      </Grid>
    </Container>
  );
};

export default Dashboard;
