import React, { useState } from 'react';
import { Card, Form, Button } from 'react-bootstrap';
import { useDispatch } from 'react-redux';
import { loginUser } from '../store/userSlice';

const Login = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleEmailChange = (e) => {
    setEmail(e.target.value);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Aquí puedes agregar la lógica para autenticar al usuario
    console.log('Email:', email);
    console.log('Password:', password);
  };

  const dispatch = useDispatch();


  return (
    <div className='LoginCard'> 
        <Card>
            <Card.Body>
                <Form onSubmit={handleSubmit}>
                <Form.Group controlId="formBasicEmail">
                    <Form.Label>Email</Form.Label>
                    <Form.Control
                    type="email"
                    placeholder="Ingresa tu email"
                    value={email}
                    onChange={handleEmailChange}
                    />
                </Form.Group>
                <br/>
                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Contraseña</Form.Label>
                    <Form.Control
                    type="password"
                    placeholder="Ingresa tu contraseña"
                    value={password}
                    onChange={handlePasswordChange}
                    />
                </Form.Group>
                <br/>
                <Button variant="primary" type="submit">
                    Iniciar sesión
                </Button>
                </Form>
            </Card.Body>
        </Card>
        <Button onClick={(e)=> {dispatch(loginUser())}}>Prueba de login</Button>
    </div>
  );
};

export default Login;