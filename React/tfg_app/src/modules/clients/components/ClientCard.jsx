import React from 'react';
import { Card, Button } from 'react-bootstrap';

const ClientCard = () => {

        return (
            <div className='client-card'>
                <Card>
                    <Card.Img variant="top"/>
                    <Card.Body>
                        <Card.Title>Client Id</Card.Title>
                        <Card.Title>Client Name</Card.Title>
                        <Card.Title>Client Last Name</Card.Title>
                        <Card.Text>DNI</Card.Text>
                        <Card.Text>Addres</Card.Text>
                        <Card.Text>City</Card.Text>
                        <Card.Text>Zip Code</Card.Text>
                        <Card.Text>Province</Card.Text>
                        <Card.Text>Email</Card.Text>
                        <Card.Text>Phone Number</Card.Text>
                        <Card.Text>Create Date</Card.Text>
                        <Card.Text>Modify date</Card.Text>
                        <Card.Text>Creator Name</Card.Text>
                        <Card.Text>Fecha de creación</Card.Text>
                        <Button variant="primary">Editar Cliente</Button>
                    </Card.Body>
                </Card>
            </div>
        );
      }

export default ClientCard;