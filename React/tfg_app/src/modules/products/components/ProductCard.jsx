import React from 'react';
import { Card, Button } from 'react-bootstrap';

const ProductCard = () => {
    
        return (
            <div className='product-card'>
                <Card>
                    <Card.Img variant="top"/>
                    <Card.Body>
                        <Card.Title>Prodcut Name</Card.Title>
                        <Card.Text>ProdcutReference</Card.Text>
                        <Card.Text>Product description</Card.Text>
                        <Card.Text>Precio</Card.Text>
                        <Card.Text>Stock: 3</Card.Text>
                        <Card.Text>Fecha de creación</Card.Text>
                        <Button variant="primary">Editar Producto</Button>
                        <Button variant="primary">Editar Stock</Button>
                    </Card.Body>
                </Card>
            </div>
        );
      }

export default ProductCard