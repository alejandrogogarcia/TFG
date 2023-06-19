import React from 'react';
import { Card, Button } from 'react-bootstrap';

const UserCard = () => {


        return (
            <div className='user-card'>
                <Card>
                    <Card.Img variant="top"/>
                    <Card.Body>
                        <Card.Title>User Id</Card.Title>
                        <Card.Title>User Name</Card.Title>
                        <Card.Title>User Last Name</Card.Title>
                        <Card.Text>DNI</Card.Text>
                        <Card.Text>Email</Card.Text>
                        <Card.Text>Birth Date</Card.Text>
                        <Card.Text>Language</Card.Text>
                        <Card.Text>Role</Card.Text>
                        <Card.Text>Create Date</Card.Text>
                        <Card.Text>Modify date</Card.Text>
                        <Card.Text>Creator Name</Card.Text>
                        <Button variant="primary">Editar Usuario</Button>
                    </Card.Body>
                </Card>
            </div>
        );
      }

export default UserCard;