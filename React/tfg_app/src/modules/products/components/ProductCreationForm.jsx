
import { Form, InputGroup, Button } from "react-bootstrap";

const ProductCreationForm = () => {

    return(

        <div className="product-creation-form">
            <h1>Crear un un nuevo producto</h1>
            <Form>
                <Form.Group>
                    <Form.Label>Name</Form.Label>
                    <Form.Control
                        type="text"
                    />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Description</Form.Label>
                    <Form.Control
                        type="textarea"
                    />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Referece</Form.Label>
                    <Form.Control
                        type="text"
                    />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Category</Form.Label>
                    <Form.Select aria-label="Default select example">
                        <option>Open this select menu</option>
                        <option value="1">One</option>
                        <option value="2">Two</option>
                        <option value="3">Three</option>
                    </Form.Select>
                </Form.Group>
                <Form.Group>
                    <Form.Label>Product price</Form.Label>
                    <InputGroup>
                        <Form.Control
                            type="number"
                        />
                        <InputGroup.Text>€</InputGroup.Text>
                    </InputGroup>
                </Form.Group>
                <Form.Group>
                    <Form.Label>Product discount</Form.Label>
                    <InputGroup>
                        <Form.Control
                            type="number"
                        />
                        <InputGroup.Text>%</InputGroup.Text>
                    </InputGroup>
                </Form.Group>
                <Form.Group>
                    <Form.Label>Product stock</Form.Label>
                    <InputGroup>
                        <Form.Control
                            type="number"
                        />
                        <InputGroup.Text>und</InputGroup.Text>
                    </InputGroup>
                </Form.Group>
                <Form.Group>
                    <Form.Label>Product image</Form.Label>
                    <Form.Control
                        type="file"
                    />
                </Form.Group>
            </Form>
            <Button>Create Product</Button>

        </div>

    )

}

export default ProductCreationForm;