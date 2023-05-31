import React, { useState } from 'react';
import { Card, Form, Button } from 'react-bootstrap';


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
    </div>
  );
};

export default Login;