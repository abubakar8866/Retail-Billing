import axios from "axios";

/*export const addItem = async (item) => {
    return await axios.post(
        `http://localhost:8080/admin/items/`,
        item,
        {
            headers:{
                'Authorization':`Bearer ${localStorage.getItem('token')}`
            }
        }
    );
}*/

export const addItem = async (item, file) => {
    const formData = new FormData();
    formData.append("item", JSON.stringify(item));
    formData.append("file", file); // file must be a File object

    console.log("Sending item:", item);
    console.log("Sending file:", file);

    return await axios.post(
        `http://localhost:8080/admin/items`,
        formData,
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`
                // Do NOT set 'Content-Type' manually here
            }
        }
    );
};

export const deleteItem = async (itemId) =>{
    return await axios.delete(`http://localhost:8080/admin/items/${itemId}`,{headers:{'Authorization':`Bearer ${localStorage.getItem('token')}`}});
}

export const fetchItems = async () => {
    return await axios.get('http://localhost:8080/items',{headers:{'Authorization':`Bearer ${localStorage.getItem('token')}`}})
}