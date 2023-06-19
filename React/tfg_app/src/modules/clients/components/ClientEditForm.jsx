
import { Form, Button } from "react-bootstrap";

const ClientEditForm = () => {

    return(

        <div className="client-edit-form">
            <h1>Modificar cliente</h1>
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
            <Button>Modify Client</Button>

        </div>

    )

}

export default ClientEditForm;