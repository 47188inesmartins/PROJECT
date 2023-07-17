import {Dropdown} from "react-bootstrap";
import * as React from "react";


function DropDownButton(props:{title,function,objectList,dropName}){
   return  <Dropdown>
        <Dropdown.Toggle
            variant="primary"
            id="category-dropdown"
            className="form-control dropdown-toggle-text"
        >
            {props.title}
        </Dropdown.Toggle>
        <Dropdown.Menu>
            {props.objectList.map((item) => (
                <Dropdown.Item
                    key={item.id}
                    onClick={() => props.function}
                >
                    {props.dropName}
                </Dropdown.Item>
            ))}
        </Dropdown.Menu>
    </Dropdown>
}