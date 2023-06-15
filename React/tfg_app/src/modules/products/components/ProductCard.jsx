import React, { useState } from 'react';
import { Card, Button } from 'react-bootstrap';
import ProductEditStock from './ProductEditStock';

const ProductCard = () => {

    const [show, setShow] = useState(false);
    
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
                <ProductEditStock
                    show={show}
                    setShow={setShow}
                />
            </div>
        );
      }

export default ProductCard