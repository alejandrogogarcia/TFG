import React, { useState } from 'react';
import { Container, Row, Col, Form, Table } from 'react-bootstrap';

const data = [
    { id: 1, name: 'Producto 1', price: 10 },
    { id: 2, name: 'Producto 2', price: 20 },
    { id: 3, name: 'Producto 3', price: 30 },
    { id: 4, name: 'Producto 4', price: 40 },
  ];

const ManageProducts = () => {

    const [searchText, setSearchText] = useState('');
    const [searchResults, setSearchResults] = useState(data);

    const handleSearch = (e) => {
        const searchText = e.target.value;
        setSearchText(searchText);

        const filteredResults = data.filter((item) =>
        item.name.toLowerCase().includes(searchText.toLowerCase())
        );

        setSearchResults(filteredResults);
    };

    return(
        <div className='manage-products'>
            <div className='search-box'>
                <h1>Búsqueda de productos</h1>
                <Form.Group>
                    <Form.Control
                    type="text"
                    placeholder="Buscar producto"
                    value={searchText}
                    onChange={handleSearch}
                    />
                </Form.Group>
            </div>
            <div className='search-result-table'>
                <Container >
                    <Row>
                        <Col>
                        <Table striped bordered hover>
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Precio</th>
                            </tr>
                            </thead>
                            <tbody>
                            {searchResults.map((item) => (
                                <tr key={item.id}>
                                <td>{item.id}</td>
                                <td>{item.name}</td>
                                <td>{item.price}</td>
                                </tr>
                            ))}
                            </tbody>
                        </Table>
                        </Col>
                    </Row>
                </Container>
            </div>
        </div>

    )

}

export default ManageProducts;