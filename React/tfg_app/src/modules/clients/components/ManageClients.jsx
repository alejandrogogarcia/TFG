
const ManageClients = () =>{

    return(
        <div className='manage-clients'>
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
                                <th>Apellido</th>
                                <th>DNI</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </Table>
                        </Col>
                    </Row>
                </Container>
            </div>
        </div>
    );
}

export default ManageClients();