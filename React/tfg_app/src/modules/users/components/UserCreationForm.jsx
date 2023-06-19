
import { Form, Button } from "react-bootstrap";

const UserCreationForm = () => {

    return(

        <div className="user-creation-form">
            <h1>Crear un un nuevo Usuario</h1>
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
                    <Form.Label>Email</Form.Label>
                    <Form.Control
                       type="text"
                    />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Birth Date</Form.Label>
                    <Form.Control
                       type="number"
                    />
                </Form.Group>
                <Form.Group>
                    <Form.Label>Language</Form.Label>
                    <Form.Select aria-label="Default select example">
                        <option>Open this select menu</option>
                        <option value="1">One</option>
                        <option value="2">Two</option>
                        <option value="3">Three</option>
                    </Form.Select>
                </Form.Group>
                <Form.Group>
                    <Form.Label>Role</Form.Label>
                    <Form.Select aria-label="Default select example">
                        <option>Open this select menu</option>
                        <option value="1">One</option>
                        <option value="2">Two</option>
                        <option value="3">Three</option>
                    </Form.Select>
                </Form.Group>
            </Form>
            <Button>Create User</Button>

        </div>

    )

}

export default UserCreationForm;