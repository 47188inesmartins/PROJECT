import * as React from "react";
import {useState} from "react";
import {Fetch} from "../Utils/useFetch";
import "../Style/CreatingCompany.css"
import { Dropdown } from "react-bootstrap";



interface CompanyInputDto {
    nif: string;
    address: string;
    name: string;
    type: string;
    description: string;
}


export function CreatingCompany(){

    const [companyName, setCompanyName] = useState<string>("");
    const [businessType, setBusinessType] = useState<string>("");
    const [address, setAddress] = useState<string>("");
    const [nif, setNif] = useState<string>("");
    const [description, setDescription] = useState<string>("");
    const [create, setCreate] = useState<Boolean>(false)
    const categories = ['BEAUTY', 'LIFESTYLE', 'FITNESS', 'BUSINESS', 'OTHERS', "EDUCATION"];
    const [selectedCategory, setSelectedCategory] = useState("");

    const handleCategorySelect = (category) => {
        setSelectedCategory(category);
    };


    const companyData : CompanyInputDto = {
        nif : nif,
        address : address,
        name: companyName,
        type:selectedCategory,
        description: description
    }


    const handleCancel = () => {
        window.location.href = '/'; // Redireciona para a página inicial (home)
    };


    const handleCreate = () => {
        setCreate(true)
    };


    function FetchCreateCompany(){
        const resp = Fetch('/company',
            'POST',
            companyData).response

        if(!resp) return(<p>...loading...</p>);

        if(resp.status) {
            setCreate(false)
            window.location.href = `/`
            return(<></>);
        }

        if(resp){
            const companyId = resp.id
            window.location.href = `/company/${companyId}/schedule`
            return(
                <></>
            )
        }
    }


    return (
        <div style = {{backgroundColor :  '#0e4378' }}>
            {!create ?
                <section className="vh-100 gradient-custom">
                    <div className="container py-5 h-100">
                        <div className="row d-flex justify-content-center align-items-center h-100">
                            <div className="col-12 col-md-8 col-lg-6 col-xl-5">
                                <div className="card bg-white text-dark" style={{borderRadius: '1rem'}}>
                                    <div className="card-body p-5 text-center">
                                        <div className="mb-md-5 mt-md-4 pb-5">
                                            <h2 className="fw-bold mb-2 text-uppercase">Register your company on our
                                                website!</h2>
                                            <div className="form-outline form-white mb-4">
                                                <input
                                                    required={true}
                                                    type="text"
                                                    id="typeEmailX"
                                                    className="form-control form-control-lg"
                                                    value={companyName}
                                                    onChange={(e) => setCompanyName(e.target.value)}
                                                />
                                                <label className="form-label" htmlFor="typeEmailX">Company name</label>
                                            </div>
                                            <div className="form-outline form-white mb-4">
                                                <Dropdown>
                                                    <Dropdown.Toggle variant="secondary">
                                                        {selectedCategory ? selectedCategory : "Select a category"}
                                                    </Dropdown.Toggle>
                                                    <Dropdown.Menu>
                                                        {categories.map((category) => (
                                                            <Dropdown.Item
                                                                key={category}
                                                                active={category === selectedCategory}
                                                                onClick={() => handleCategorySelect(category)}
                                                            >
                                                                {category}
                                                            </Dropdown.Item>
                                                        ))}
                                                    </Dropdown.Menu>
                                                </Dropdown>
                                                <label className="form-label" htmlFor="typePasswordX">Type of
                                                    business</label>
                                            </div>
                                            <div className="form-outline form-white mb-4">
                                                <input
                                                    required={true}
                                                    type="text"
                                                    id="typePasswordX"
                                                    className="form-control form-control-lg"
                                                    value={address}
                                                    onChange={(e) => setAddress(e.target.value)}
                                                />
                                                <label className="form-label" htmlFor="typePasswordX">Address</label>
                                            </div>
                                            <div className="form-outline form-white mb-4">
                                                <input
                                                    required={true}
                                                    type="text"
                                                    id="typePasswordX"
                                                    className="form-control form-control-lg"
                                                    value={nif}
                                                    onChange={(e) => setNif(e.target.value)}
                                                />
                                                <label className="form-label" htmlFor="typePasswordX">NIF</label>
                                            </div>
                                            <div className="form-outline form-white mb-4">
                                                <input
                                                    required={true}
                                                    type="text"
                                                    id="typePasswordX"
                                                    className="form-control form-control-lg"
                                                    value={description}
                                                    onChange={(e) => setDescription(e.target.value)}
                                                />
                                                <label className="form-label"
                                                       htmlFor="typePasswordX">Description</label>
                                            </div>

                                            <button className="btn btn-outline-light btn-lg px-5" type="submit"
                                                    onClick={handleCreate} style={{backgroundColor : 'black'}}>
                                                Next
                                            </button>
                                            <br/>
                                            <br/>
                                            <br/>

                                            <button className="btn btn-outline-light btn-lg px-5" type="submit"
                                                    onClick={handleCancel} style={{backgroundColor : 'black'}}>Cancel
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
                :
                <FetchCreateCompany />
            }
        </div>
    );
}