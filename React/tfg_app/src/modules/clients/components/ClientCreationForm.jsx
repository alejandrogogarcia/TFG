
import { Form, Button } from "react-bootstrap";

const ClientCreationForm = () => {

    return(

        <div className="client-creation-form">
            <h1>Crear un un nuevo cliente</h1>
            <Form>
                
                <Form.Group>
                    <Form.Label>DNI</Form.Label>
                    <Form.Control
                        type="text"
                    />
                </Form.Group>
                <Form.Group>
                    <Form.Label>First Name</Form.Label>
                    <Form.Control
                        type="text"
                    />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Last Name</Form.Label>
                    <Form.Control
                        type="text"
                    />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Addres</Form.Label>
                    <Form.Control
                       type="text"
                    />
                </Form.Group>
                <Form.Group>
                    <Form.Label>City</Form.Label>
                    <Form.Control
                       type="text"
                    />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Post Code</Form.Label>
                    <Form.Control
                       type="number"
                    />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Province</Form.Label>
                    <Form.Control
                       type="text"
                    />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Email</Form.Label>
                    <Form.Control
                       type="text"
                    />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Phone Number</Form.Label>
                    <Form.Control
                       type="number"
                    />
                </Form.Group>
            </Form>
            <Button>Create Client</Button>

        </div>

    )

}

export default ClientCreationForm;