import * as React from "react";
import {useState} from "react";
import {Fetch} from "../Utils/useFetch";
import "../Style/CreatingCompany.css"
import {useParams} from "react-router-dom";

export function CreatingServices(){
    const [create, setCreate] = useState<Boolean>(false)
    const [selectedFeatures, setSelectedFeatures] = useState([]);
    const [isOpen, setIsOpen] = useState(false);
    const [selectedValue, setSelectedValue] = useState(null);

    const renderOptions = () => {
        const options = [];
        for (let i = 1; i <= 200; i++) {
            options.push(
                <div
                    key={i}
                    className="dropdown-option"
                    onClick={() => handleOptionClick(i)}
                >
                    {i} minutes
                </div>
            );
        }
        return options;
    };

    const handleDropdownToggle = () => {
        setIsOpen(!isOpen);
    };

    const handleOptionClick = (value) => {
        setSelectedValue(
            value
            //  convertMinutesToHHMMSS(value)
        );
        setIsOpen(false);
    };

    const id = useParams().id
    const handleCancel = () => {
        window.location.href = `/company/${id}/upload-file`;
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
        setServices(updatedServices);
    };

    function FetchCreateServices() {
        const params = useParams()
        const id = params.id
        Fetch(`/service/company/${id}`,
            'POST',
            services[0])
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
        const handleFeatureChange = (employeeId) => {
            if (selectedFeatures.includes(employeeId)) {
                setSelectedFeatures(selectedFeatures.filter((id) => id !== employeeId));
            } else {
                setSelectedFeatures([...selectedFeatures, employeeId]);
            }
            handleServiceChange(props.index, 'users', selectedFeatures);
        };


        return (
            <fieldset style={{ backgroundColor: "gray", padding: "10px" }}>
                <legend>Employees that perform this service:</legend>
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
                                    onChange={() => handleFeatureChange(employee.id)}
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
                                                        <div className="dropdown">
                                                            <label>
                                                                Duration
                                                            </label>
                                                            <br />
                                                            <button className="dropdown-toggle" onClick={handleDropdownToggle}>
                                                                {selectedValue?
                                                                    `${selectedValue} minutes`:
                                                                    "Select a number"
                                                                }
                                                            </button>
                                                            {isOpen && (
                                                                <div className="dropdown-options-container">
                                                                    <div className="dropdown-options">{renderOptions()}</div>
                                                                </div>
                                                            )}
                                                        </div>
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