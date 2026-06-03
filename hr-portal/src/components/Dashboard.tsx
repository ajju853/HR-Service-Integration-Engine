import React from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, Typography, Button, Box, Paper, Grid, Card, CardContent, CardActions } from '@mui/material';
import PersonAddIcon from '@mui/icons-material/PersonAdd';
import PeopleIcon from '@mui/icons-material/People';

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
              <PersonAddIcon sx={{ fontSize: 48, color: 'primary.main', mb: 1 }} />
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
              <PeopleIcon sx={{ fontSize: 48, color: 'secondary.main', mb: 1 }} />
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
