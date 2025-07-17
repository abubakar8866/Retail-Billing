import UserForm from '../../components/UserForm/UserForm';
import UsersList from '../../components/UserList/UserList';
import './ManageUsers.css';
const ManageUsers = () => {
    return (
        <div className="user-container text-light">
            <div className="left-column">
                <UserForm/>
            </div>
            <div className="right-column">
                <UsersList/>
            </div>
        </div>
    );
}

export default ManageUsers;