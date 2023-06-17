import React, { useState } from 'react';
import { Card, Button, Modal } from 'react-bootstrap';
import ProductEditStock from './ProductEditStock';

const ProductCard = () => {

    const [showModal, setShowModal] = useState(false);
    
      const handleShowModal = () => {
        setShowModal(true);
      };
    
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
                        <Button variant="primary" onClick={handleShowModal}>Editar Stock</Button>
                    </Card.Body>
                </Card>
                <ProductEditStock
                    showModal={showModal}
                    setShowModal={setShowModal}
                /> 

                {/* <Modal show={showModal} onHide={handleClose}>
                    <Modal.Header closeButton>
                    <Modal.Title>Título do Modal</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                    <p>Conteúdo do modal...</p>
                    </Modal.Body>
                    <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Fechar
                    </Button>
                    <Button variant="primary" onClick={handleClose}>
                        Salvar
                    </Button>
                    </Modal.Footer>
                </Modal> */}
            </div>
        );
      }

export default ProductCard