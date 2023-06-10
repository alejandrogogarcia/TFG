import React, { useState } from 'react';
import { Form, Button } from 'react-bootstrap';

const PasswordUpdate = () => {

    const [currentPassword, setCurrentPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');

    const handleSubmit = (e) => {
    e.preventDefault();
    // Lógica para enviar la solicitud de cambio de contraseña
    // Aquí puedes hacer una llamada a una API o realizar las validaciones necesarias
    console.log('Current Password:', currentPassword);
    console.log('New Password:', newPassword);
    console.log('Confirm Password:', confirmPassword);
  };


    return(
        <div className='pass-update'>
            <h4>Cambio de contraseña</h4>
            <Form onSubmit={handleSubmit}>
                <Form.Group controlId="currentPassword">
                    <Form.Label>Contraseña actual</Form.Label>
                    <Form.Control
                    type="password"
                    value={currentPassword}
                    onChange={(e) => setCurrentPassword(e.target.value)}
                    />
                </Form.Group>
                <Form.Group controlId="newPassword">
                    <Form.Label>Nueva contraseña</Form.Label>
                    <Form.Control
                    type="password"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                    />
                </Form.Group>
                <Form.Group controlId="confirmPassword">
                    <Form.Label>Confirmar contraseña</Form.Label>
                    <Form.Control
                    type="password"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    />
                </Form.Group>
                <Button variant="primary" type="submit">
                    Cambiar contraseña
                </Button>
            </Form>
        </div>
    );
}

export default PasswordUpdate;