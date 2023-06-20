import * as React from "react";
import {useState} from "react";
import {Fetch} from "../Utils/useFetch";
import {Navigate} from "react-router";
import "../Style/CreatingCompany.css"
import {useParams} from "react-router-dom";



interface CompanyInputDto {
    nif: string;
    address: string;
    name: string;
    type: string;
    description: string;
}


export function CreatingServices(){
    const [companyName, setCompanyName] = useState<string>("");
    const [businessType, setBusinessType] = useState<string>("");
    const [address, setAddress] = useState<string>("");
    const [nif, setNif] = useState<string>("");
    const [description, setDescription] = useState<string>("");
    const [create, setCreate] = useState<Boolean>(false)
    const [employees, setEmployees] = useState(false);
    const [selectedFeatures, setSelectedFeatures] = useState([]);

    const companyData : CompanyInputDto = {
        nif : nif,
        address : address,
        name: companyName,
        type:businessType,
        description: description
    }
    const id = useParams().id
    const handleCancel = () => {
        window.location.href = `/company/${id}/upload-file`; // Redireciona para a página inicial (home)
    };

    const handleCreate = () => {
        setCreate(true)
    };

    const [services, setServices] = useState([{ serviceName: '', duration: '', price: '', users:[] }]);

    const addService = () => {
        setServices(prevServices => [...prevServices, { serviceName: '', duration: '', price: '', users:[] }]);
    };

    const handleServiceChange = (index, field, value) => {
        const updatedServices = [...services];
        updatedServices[index][field] = value;
        console.log('E',updatedServices);
        setServices(updatedServices);
    };


    const [textBoxes, setTextBoxes] = useState([""]);

    const addTextBox = () => {
        setTextBoxes(prevTextBoxes => [...prevTextBoxes, ""]);
    };

    const handleTextBoxChange = (index, value) => {
        const updatedTextBoxes = [...textBoxes];
        updatedTextBoxes[index] = value;
        console.log(textBoxes)
        setTextBoxes(updatedTextBoxes);
    };

    function FetchCreateServices() {
        const params = useParams()
        const id = params.id
        console.log("AQUIIIIIIIII",textBoxes)
        console.log("SERIVICEEEEEEEEEEEEE",services)
        const resp = Fetch(`/service/company/${id}`,
            'POST',
            services[0])
        console.log(resp)
        window.location.href = `/company/${id}/employees`
        return(<></>);
    }

    function Employees(props:{index}) {

        const cid = useParams().id
        const response = Fetch(`company/${cid}/employees`,'GET')
        console.log("Employees",response)

        if(response.response){
            if(response.response.length === 0){
                return <> No employees found </>
            }
        }
        const handleFeatureChange = (event) => {
            const selectedId = event.target.value;

            if (selectedFeatures.includes(selectedId)) {
                setSelectedFeatures(selectedFeatures.filter((id) => id !== selectedId));
            } else {
                setSelectedFeatures([...selectedFeatures, selectedId]);
            }

            handleServiceChange(props.index, 'users', selectedFeatures);
        };


        return (
            <fieldset style={{ backgroundColor: "gray", padding: "10px" }}>
                <legend>Choose your monster's features:</legend>
                {response.response?
                    <>
                        {response.response.map((employee) => (
                            <div key={employee.id}>
                                <input
                                    type="checkbox"
                                    id={employee.id}
                                    name={employee.id}
                                    value={employee.id}
                                    checked={selectedFeatures.includes(employee.id)}
                                    onChange={handleFeatureChange}
                                />
                                <label htmlFor={employee.id}>{employee.name}</label>
                            </div>
                        ))}
                    </>  : <></>
                }
            </fieldset>
        );
    }

    return (
        <div>
            {!create ? (
                <section className="vh-100 gradient-custom">
                    <div className="container py-5 h-100">
                        <div className="row d-flex justify-content-center align-items-center h-100">
                            <div className="col-12 col-md-8 col-lg-6 col-xl-5">
                                <div className="card bg-dark text-white" style={{ borderRadius: '1rem' }}>
                                    <div className="card-body p-5 text-center">
                                        <div className="mb-md-5 mt-md-4 pb-5">
                                            <h2 className="fw-bold mb-2 text-uppercase">
                                                What services does your company perform?
                                            </h2>
                                            <br /><br />
                                            {services.map((service, index) => (
                                                <div key={index}>
                                                    <div className="form-outline form-white mb-4">
                                                        <input
                                                            type="text"
                                                            id={`serviceName-${index}`}
                                                            className="form-control form-control-lg"
                                                            value={service.serviceName}
                                                            onChange={e => handleServiceChange(index, 'serviceName', e.target.value)}
                                                        />
                                                        <label className="form-label" htmlFor={`serviceName-${index}`}>
                                                            Service Name
                                                        </label>
                                                    </div>
                                                    <div className="form-outline form-white mb-4">
                                                        <input
                                                            type="text"
                                                            id={`serviceDuration-${index}`}
                                                            className="form-control form-control-lg"
                                                            value={service.duration}
                                                            onChange={e => handleServiceChange(index, 'duration', e.target.value)}
                                                        />
                                                        <label className="form-label" htmlFor={`serviceDuration-${index}`}>
                                                            Duration
                                                        </label>
                                                    </div>
                                                    <div className="form-outline form-white mb-4">
                                                        <input
                                                            type="text"
                                                            id={`servicePrice-${index}`}
                                                            className="form-control form-control-lg"
                                                            value={service.price}
                                                            onChange={e => handleServiceChange(index, 'price', e.target.value)}
                                                        />
                                                        <label className="form-label" htmlFor={`servicePrice-${index}`}>
                                                            Price
                                                        </label>
                                                    </div>
                                                    <Employees index={index}/>
                                                </div>
                                            ))}
                                        </div>
                                        <button onClick={addService} className="btn btn-dark" style={{ backgroundColor: 'blueviolet' }}>
                                            <i className="fa fa-plus"></i>
                                        </button>
                                        <br />
                                        <br />
                                        <br />
                                        <button className="btn btn-outline-light btn-lg px-5" type="submit" onClick={handleCreate}>
                                            Next
                                        </button>
                                        <br />
                                        <br />
                                        <br />
                                        <button className="btn btn-outline-light btn-lg px-5" type="submit" onClick={handleCancel}>
                                            Configure later
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            ) : (
                <FetchCreateServices/>
            )}
        </div>
    );
}

function FetchEmployees(){
    const cid = useParams().id
    const response = Fetch(`company/${cid}/employees`,'GET')
    console.log("Employees",response)

    if(response.response){
        if(response.response.length === 0){
            return <> No employees found </>
        }
    }
    return(
        <>
        {response.response?
                <>
                {response.response.map((employee) => (
                        <option key={employee.id} value={employee.id}>
                            {employee.name}
                        </option>
                    ))}</>:
                <>No employees found</>
        }
        </>
    )
}

