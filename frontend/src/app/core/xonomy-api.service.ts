import { Injectable } from '@angular/core';
declare const Xonomy: any;

@Injectable({
  providedIn: 'root'
})
export class XonomyApiService {
  static metadataPrefix = "pred:";
  static scientificPublicationPrefix = "https://github.com/milica152/XML_tim27";
  static personPrefix = "https://github.com/milica152/XML_tim27/person/";
  constructor() {
  }

  public scientificPublicationSpecification = {
    elements: {
      "sp:scientificPublication": {
        menu: [{
          caption: "Add RDF attribute 'about'",
          action: Xonomy.newAttribute,
          actionParameter: {name:"about", value:XonomyApiService.scientificPublicationPrefix},
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("about");
          }
        }, {
          caption: "Add RDF attribute 'typeof'",
          action: Xonomy.newAttribute,
          actionParameter: {name:"typeof", value:XonomyApiService.metadataPrefix},
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("typeof");
          }
        }, {
          caption: "Add <sp:metadata>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:metadata xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:metadata>"
        }, {
          caption: "Add <sp:abstract>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:abstract xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:abstract>"
        }, {
          caption: "Add <sp:chapter>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:chapter xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:chapter>"
        }, {
          caption: "Add <sp:references>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:references xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:references>"
        }],
        attributes: {
          "about": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this @about",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },
      "sp:metadata": {
        mustBeBefore: ["sp:abstract", "sp:chapter", "sp:references"],
        menu: [{
          caption: "Add <sp:title>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:title xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:title>"
        }, {
          caption: "Add <sp:authors>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:authors xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:authors>"
        }, {
          caption: "Add <sp:keywords>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:keywords xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:keywords>"
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:title": {
        mustBeBefore: ["sp:authors", "sp:keywords", "sp:paragraph"],
        oneliner: true,
        hasText: true,
        asker: Xonomy.askString,
        menu: [{
          caption: "Add RDF attribute 'property'",
          action: Xonomy.newAttribute,
          actionParameter: {name:"property", value:XonomyApiService.metadataPrefix},
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("property");
          }
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }],
        attributes: {
          "property": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this @property",
              action: Xonomy.deleteAttribute
            }]
          }
        }},
        "sp:authors": {
          menu: [{
            caption: "Add <sp:author>",
            action: Xonomy.newElementChild,
            actionParameter: "<sp:author xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:author>"
          }, {
            caption: "Delete element",
            action: Xonomy.deleteElement
          }]
        },
        "sp:author": {
          menu: [{
            caption: "Add RDF attribute 'href'",
            action: Xonomy.newAttribute,
            actionParameter: {name:"href", value:XonomyApiService.personPrefix},
            hideIf: function (jsElement) {
              return jsElement.hasAttribute("href");
            }
          }, {
            caption: "Add RDF attribute 'rel'",
            action: Xonomy.newAttribute,
            actionParameter: {name:"rel", value:XonomyApiService.metadataPrefix},
            hideIf: function (jsElement) {
              return jsElement.hasAttribute("rel");
            }
          }, {
            caption: "Add RDF attribute 'typeof'",
            action: Xonomy.newAttribute,
            actionParameter: {name:"typeof", value:XonomyApiService.metadataPrefix},
            hideIf: function (jsElement) {
              return jsElement.hasAttribute("typeof");
            }
          }, {
            caption: "Add <sp:name>",
            action: Xonomy.newElementChild,
            actionParameter: "<sp:name xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:name>"
          }, {
            caption: "Add <sp:affiliation>",
            action: Xonomy.newElementChild,
            actionParameter: "<sp:affiliation xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:affiliation>"
          }, {
            caption: "Add <sp:city>",
            action: Xonomy.newElementChild,
            actionParameter: "<sp:city xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:city>"
          }, {
            caption: "Add <sp:state>",
            action: Xonomy.newElementChild,
            actionParameter: "<sp:state xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:state>"
          }, {
            caption: "Add <sp:email>",
            action: Xonomy.newElementChild,
            actionParameter: "<sp:email xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:email>"
          }, {
            caption: "Delete element",
            action: Xonomy.deleteElement
          }],
          attributes: {
            "href": {
              asker: Xonomy.askString,
              menu: [{
                caption: "Delete this @href",
                action: Xonomy.deleteAttribute
              }]
            },
            "rel": {
              asker: Xonomy.askString,
              menu: [{
                caption: "Delete this @rel",
                action: Xonomy.deleteAttribute
              }]
            },
            "typeof": {
              asker: Xonomy.askString,
              menu: [{
                caption: "Delete this @typeof",
                action: Xonomy.deleteAttribute
              }]
            }
          }
        },
      "sp:name" : {
        mustBeBefore: ["sp:affiliation", "sp:city", "sp:state", "sp:email"],
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
        menu: [{
          caption: "Add RDF attribute 'about'",
          action: Xonomy.newAttribute,
          actionParameter: {name:"about", value:XonomyApiService.personPrefix},
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("about");
          }
        },{
          caption: "Add RDF attribute 'property'",
          action: Xonomy.newAttribute,
          actionParameter: {name:"property", value:XonomyApiService.metadataPrefix},
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("property");
          }
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }],
        attributes: {
          "about": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this @about",
              action: Xonomy.deleteAttribute
            }]
          },
          "property": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this @property",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },
      "sp:affiliation" : {
        mustBeBefore: ["sp:city", "sp:state", "sp:email"],
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
        menu: [{
          caption: "Add RDF attribute 'about'",
          action: Xonomy.newAttribute,
          actionParameter: {name:"about", value:XonomyApiService.personPrefix},
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("about");
          }
        },{
          caption: "Add RDF attribute 'property'",
          action: Xonomy.newAttribute,
          actionParameter: {name:"property", value:XonomyApiService.metadataPrefix},
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("property");
          }
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }],
        attributes: {
          "about": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this @about",
              action: Xonomy.deleteAttribute
            }]
          },
          "property": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this @property",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },
      "sp:city": {
        mustBeBefore: ["sp:state", "sp:email"],
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:state": {
        mustBeBefore: ["sp:email"],
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:email": {
        asker: Xonomy.askString,
        hasText: true,
        oneliner: true,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:keywords": {
        mustBeAfter: ["sp:title", "sp:authors"],
        menu: [{
          caption: "Add <sp:keyword>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:keyword xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:keyword>"
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:keyword": {
        asker: Xonomy.askString,
        hasText: true,
        oneliner: true,
        menu: [{
          caption: "Add RDF attribute 'property'",
          action: Xonomy.newAttribute,
          actionParameter: {name:"property", value:XonomyApiService.metadataPrefix},
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("property");
          }
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }],
        attributes: {
          "property": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this @property",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },
      "sp:abstract": {
        mustBeBefore: ["sp:chapter", "sp:references"],
        menu: [{
          caption: "Add attribute 'sp:id'",
          action: Xonomy.newAttribute,
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("sp:id");
          }
        },{
          caption: "Add <sp:paragraph>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:paragraph xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:paragraph>"
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }],
        attributes: {
          "sp:id": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this @sp:id",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },
      "sp:paragraph": {
        hasText: true,
        menu: [{
          caption: "Add attribute 'sp:id'",
          action: Xonomy.newAttribute,
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("sp:id");
          }
        },{
          caption: "Add <sp:boldText>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:boldText xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:boldText>"
        },{
          caption: "Add <sp:emphasizedText>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:emphasizedText xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:emphasizedText>"
        },{
          caption: "Add <sp:quote>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:quote xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:quote>"
        },{
          caption: "Add <sp:figure>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:figure xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:figure>"
        },{
          caption: "Add <sp:list>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:list xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:list>"
        },{
          caption: "Add <sp:code>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:code xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:code>"
        },{
          caption: "Add <sp:table>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:table xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:table>"
        },{
          caption: "Add <sp:referencePointer>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:referencePointer xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:referencePointer>"
        },{
          caption: "Add <sp:mathExpression>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:mathExpression xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:mathExpression>"
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }],
        attributes: {
          "sp:id": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this @sp:id",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },
      "sp:boldText": {
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:emphasizedText": {
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:quote": {
        menu:[{
          caption: "Add <sp:source>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:source xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:source>"
        },{
          caption: "Add <sp:quoteContent>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:quoteContent xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:quoteContent>"
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:source": {
        hasText: true,
        oneliner: true,
        mustBeBefore: ["sp:quoteContent"],
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:quoteContent": {
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:figure": {
        menu: [{
          caption: "Add attribute 'sp:id'",
          action: Xonomy.newAttribute,
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("sp:id");
          }
        },{
          caption: "Add <sp:description>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:description xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:description>"
        },{
          caption: "Add <sp:image>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:image xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:image>"
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }],
        attributes: {
          "sp:id": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this @sp:id",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },
      "sp:description": {
        mustBeBefore: ["sp:image"],
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:image": {
        asker: Xonomy.askLongString,
        hasText: true,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:list": {
        menu: [{
          caption: "Add attribute 'sp:id'",
          action: Xonomy.newAttribute,
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("sp:id");
          }
        },{
          caption: "Add attribute 'ordered'",
          action: Xonomy.newAttribute,
          actionParameter: {name: "ordered", value: "false"},
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("ordered");
          }
        },{
          caption: "Add <sp:listItem>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:listItem xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:listItem>"
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }],
        attributes: {
          "sp:id": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this @sp:id",
              action: Xonomy.deleteAttribute
            }]
          },
          "ordered": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this @ordered",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },
      "sp:listItem": {
        asker: Xonomy.askString,
        hasText: true,
        oneliner: true,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:code": {
        asker: Xonomy.askString,
        hasText: true,
        oneliner: true,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:table": {
        menu: [{
          caption: "Add attribute 'sp:id'",
          action: Xonomy.newAttribute,
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("sp:id");
          }
        },{
          caption: "Add <sp:tableDescription>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:tableDescription xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:tableDescription>"
        },{
          caption: "Add <sp:tableRow>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:tableRow xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:tableRow>"
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }],
        attributes: {
          "sp:id": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this @sp:id",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },
      "sp:tableDescription": {
        mustBeBefore: ["sp:tableRow"],
        asker: Xonomy.askString,
        hasText: true,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:tableRow": {
        menu: [{
          caption: "Add <sp:tableCell>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:tableCell xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:tableCell>"
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:tableCell": {
        asker: Xonomy.askString,
        hasText: true,
        oneliner: true,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:referencePointer": {
        asker: Xonomy.askString,
        hasText: true,
        oneliner: true,
        menu: [{
          caption: "Add attribute 'sp:id'",
          action: Xonomy.newAttribute,
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("sp:id");
          }
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }],
        attributes: {
          "sp:id": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this @sp:id",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },
      "sp:mathExpression": {
        hasText: true,
        menu: [{
          caption: "Add <sp:sum>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:sum xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:sum>"
        },{
          caption: "Add <sp:limit>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:limit xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:limit>"
        },{
          caption: "Add <sp:integral>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:integral xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:integral>"
        },{
          caption: "Add <sp:anyExpression>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:anyExpression xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:anyExpression>"
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:sum": {
        mustBeBefore: ["sp:limit", "sp:integral", "sp:anyExpression"],
        menu: [{
          caption: "Add <sp:counter>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:counter xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:counter>"
        },{
          caption: "Add <sp:begin>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:begin xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:begin>"
        },{
          caption: "Add <sp:end>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:end xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:end>"
        },{
          caption: "Add <sp:content>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:content xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:content>"
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:limit": {
        mustBeBefore: ["sp:integral", "sp:anyExpression"],
        menu: [{
          caption: "Add <sp:variable>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:variable xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:variable>"
        },{
          caption: "Add <sp:target>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:target xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:target>"
        },{
          caption: "Add <sp:content>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:content xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:content>"
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:integral": {
        mustBeBefore: ["sp:anyExpression"],
        menu: [{
          caption: "Add <sp:begin>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:begin xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:begin>"
        },{
          caption: "Add <sp:end>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:end xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:end>"
        },{
          caption: "Add <sp:content>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:content xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:content>"
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:counter": {
        mustBeBefore: ["sp:begin", "sp:end", "sp:content"],
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:begin": {
        mustBeBefore: ["sp:end", "sp:content"],
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:end": {
        mustBeBefore: ["sp:content"],
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:variable": {
        mustBeBefore: ["sp:target", "sp:content"],
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:target": {
        mustBeBefore: ["sp:content"],
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:content": {
        hasText: true,
        menu: [{
          caption: "Add <sp:sum>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:sum xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:sum>"
        },{
          caption: "Add <sp:limit>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:limit xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:limit>"
        },{
          caption: "Add <sp:integral>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:integral xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:integral>"
        },{
          caption: "Add <sp:anyExpression>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:anyExpression xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:anyExpression>"
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:anyExpression": {
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:chapter": {
        mustBeBefore: ["sp:references"],
        menu: [{
          caption: "Add attribute 'sp:id'",
          action: Xonomy.newAttribute,
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("sp:id");
          }
        },{
          caption: "Add attribute 'level'",
          action: Xonomy.newAttribute,
          actionParameter: {name: "level", value: 1},
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("level");
          }
        },{
          caption: "Add <sp:title>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:title xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:title>"
        },{
          caption: "Add <sp:paragraph>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:paragraph xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:paragraph>"
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }],
        attributes: {
          "sp:id": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this @sp:id",
              action: Xonomy.deleteAttribute
            }]
          },
          "level": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this @level",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },
      "sp:references": {
        menu:[{
          caption: "Add <sp:reference>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:reference xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:reference>"
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:reference": {
        menu: [{
          caption: "Add attribute 'sp:id'",
          action: Xonomy.newAttribute,
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("sp:id");
          }
        },{
          caption: "Add attribute 'refPartId'",
          action: Xonomy.newAttribute,
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("refPartId");
          }
        },{
          caption: "Add RDF attribute 'href'",
          action: Xonomy.newAttribute,
          actionParameter: {name:"href", value:XonomyApiService.scientificPublicationPrefix},
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("href");
          }
        },{
          caption: "Add RDF attribute 'rel'",
          action: Xonomy.newAttribute,
          actionParameter: {name:"rel", value:XonomyApiService.metadataPrefix},
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("rel");
          }
        },{
          caption: "Add <sp:referenceAuthors>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:referenceAuthors xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:referenceAuthors>"
        },{
          caption: "Add <sp:yearIssued>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:yearIssued xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:yearIssued>"
        },{
          caption: "Add <sp:referenceTitle>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:referenceTitle xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:referenceTitle>"
        },{
          caption: "Add <sp:publisherName>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:publisherName xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:publisherName>"
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }],
        attributes: {
          "sp:id": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this @sp:id",
              action: Xonomy.deleteAttribute
            }]
          },
          "refPartId": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this @refPartId",
              action: Xonomy.deleteAttribute
            }]
          },
          "href": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this @href",
              action: Xonomy.deleteAttribute
            }]
          },
          "rel": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this @rel",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },
      "sp:referenceAuthors": {
        mustBeBefore: ["sp:yearIssued", "sp:referenceTitle", "sp:publisherName"],
        menu:[{
          caption: "Add <sp:referenceAuthor>",
          action: Xonomy.newElementChild,
          actionParameter: "<sp:referenceAuthor xmlns:sp=\"https://github.com/milica152/XML_tim27\"></sp:referenceAuthor>"
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:referenceAuthor": {
        asker: Xonomy.askString,
        hasText: true,
        oneliner: true,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:yearIssued": {
        mustBeBefore: ["sp:referenceTitle", "sp:publisherName"],
        asker: Xonomy.askString,
        hasText: true,
        oneliner: true,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:referenceTitle": {
        mustBeBefore: ["sp:publisherName"],
        asker: Xonomy.askString,
        hasText: true,
        oneliner: true,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "sp:publisherName": {
        asker: Xonomy.askString,
        hasText: true,
        oneliner: true,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      }
    }
  };

  public coverLetterSpecification = {
    elements: {
      "cl:coverLetter": {
        menu: [
          {
            caption: "Add <cl:publicationTitle>",
            action: Xonomy.newElementChild,
            actionParameter: "<cl:publicationTitle xmlns:cl=\"http://ftn.uns.ac.rs/coverLetter\"></cl:publicationTitle>"
          },{
            caption: "Add <cl:author>",
            action: Xonomy.newElementChild,
            actionParameter: "<cl:author xmlns:cl=\"http://ftn.uns.ac.rs/coverLetter\"></cl:author>"
          },{
            caption: "Add <cl:targetPublisher>",
            action: Xonomy.newElementChild,
            actionParameter: "<cl:targetPublisher xmlns:cl=\"http://ftn.uns.ac.rs/coverLetter\"></cl:targetPublisher>"
          },{
            caption: "Add <cl:content>",
            action: Xonomy.newElementChild,
            actionParameter: "<cl:content xmlns:cl=\"http://ftn.uns.ac.rs/coverLetter\"></cl:content>"
          }
        ]
      },
      "cl:publicationTitle": {
        hasText: true,
        mustBeBefore: ["cl:author", "cl:targetPublisher", "cl:content"],
        oneliner: true,
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "cl:author": {
        menu: [{
          caption: "Add <cl:name>",
          action: Xonomy.newElementChild,
          actionParameter: "<cl:name xmlns:cl=\"http://ftn.uns.ac.rs/coverLetter\"></cl:name>"
        },{
          caption: "Add <cl:educationTitle>",
          action: Xonomy.newElementChild,
          actionParameter: "<cl:educationTitle xmlns:cl=\"http://ftn.uns.ac.rs/coverLetter\"></cl:educationTitle>"
        },{
          caption: "Add <cl:affiliation>",
          action: Xonomy.newElementChild,
          actionParameter: "<cl:affiliation xmlns:cl=\"http://ftn.uns.ac.rs/coverLetter\"></cl:affiliation>"
        },{
          caption: "Add <cl:state>",
          action: Xonomy.newElementChild,
          actionParameter: "<cl:state xmlns:cl=\"http://ftn.uns.ac.rs/coverLetter\"></cl:state>"
        },{
          caption: "Add <cl:city>",
          action: Xonomy.newElementChild,
          actionParameter: "<cl:city xmlns:cl=\"http://ftn.uns.ac.rs/coverLetter\"></cl:city>"
        },{
          caption: "Add <cl:phoneNumber>",
          action: Xonomy.newElementChild,
          actionParameter: "<cl:phoneNumber xmlns:cl=\"http://ftn.uns.ac.rs/coverLetter\"></cl:phoneNumber>"
        },{
          caption: "Add <cl:email>",
          action: Xonomy.newElementChild,
          actionParameter: "<cl:email xmlns:cl=\"http://ftn.uns.ac.rs/coverLetter\"></cl:email>"
        },{
          caption: "Add <cl:signature>",
          action: Xonomy.newElementChild,
          actionParameter: "<cl:signature xmlns:cl=\"http://ftn.uns.ac.rs/coverLetter\"></cl:signature>"
        },{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "cl:name": {
        hasText: true,
        mustBeBefore: ["cl:educationTitle", "cl:affiliation", "cl:city", "cl:state", "cl:phoneNumber", "cl:email", "cl:signature"],
        oneliner: true,
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "cl:educationTitle": {
        hasText: true,
        mustBeBefore: ["cl:affiliation", "cl:city", "cl:state", "cl:phoneNumber", "cl:email", "cl:signature"],
        oneliner: true,
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "cl:affiliation": {
        hasText: true,
        mustBeBefore: ["cl:city", "cl:state", "cl:phoneNumber", "cl:email", "cl:signature"],
        oneliner: true,
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "cl:city": {
        hasText: true,
        mustBeBefore: ["cl:state", "cl:phoneNumber", "cl:email", "cl:signature"],
        oneliner: true,
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "cl:state": {
        hasText: true,
        mustBeBefore: ["cl:phoneNumber", "cl:email", "cl:signature"],
        oneliner: true,
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "cl:phoneNumber": {
        hasText: true,
        mustBeBefore: ["cl:email", "cl:signature"],
        oneliner: true,
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "cl:email": {
        hasText: true,
        mustBeBefore: ["cl:signature"],
        oneliner: true,
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "cl:signature": {
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "cl:targetPublisher": {
        menu: [{
          caption: "Add <cl:editor>",
          action: Xonomy.newElementChild,
          actionParameter: "<cl:editor xmlns:cl=\"http://ftn.uns.ac.rs/coverLetter\"></cl:editor>"
        },{
          caption: "Add <cl:journal>",
          action: Xonomy.newElementChild,
          actionParameter: "<cl:journal xmlns:cl=\"http://ftn.uns.ac.rs/coverLetter\"></cl:journal>"
        },{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "cl:editor": {
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
        mustBeBefore: ["cl:journal"],
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "cl:journal": {
        hasText: true,
        oneline: true,
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "cl:content": {
        menu:[{
          caption: "Add <cl:paragraph>",
          action: Xonomy.newElementChild,
          actionParameter: "<cl:paragraph xmlns:cl=\"http://ftn.uns.ac.rs/coverLetter\"></cl:paragraph>"
        },{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "cl:paragraph": {
        hasText: true,
        menu:[{
          caption: "Add <cl:boldText>",
          action: Xonomy.newElementChild,
          actionParameter: "<cl:boldText xmlns:cl=\"http://ftn.uns.ac.rs/coverLetter\"></cl:boldText>"
        },{
          caption: "Add <cl:emphasizedText>",
          action: Xonomy.newElementChild,
          actionParameter: "<cl:emphasizedText xmlns:cl=\"http://ftn.uns.ac.rs/coverLetter\"></cl:emphasizedText>"
        },{
          caption: "Add <cl:quote>",
          action: Xonomy.newElementChild,
          actionParameter: "<cl:quote xmlns:cl=\"http://ftn.uns.ac.rs/coverLetter\"></cl:quote>"
        },{
          caption: "Add <cl:list>",
          action: Xonomy.newElementChild,
          actionParameter: "<cl:list xmlns:cl=\"http://ftn.uns.ac.rs/coverLetter\"></cl:list>"
        },{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "cl:boldText": {
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "cl:emphasizedText": {
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "cl:quote": {
        menu:[{
          caption: "Add <cl:source>",
          action: Xonomy.newElementChild,
          actionParameter: "<cl:source xmlns:cl=\"http://ftn.uns.ac.rs/coverLetter\"></cl:source>"
        },{
          caption: "Add <cl:quoteContent>",
          action: Xonomy.newElementChild,
          actionParameter: "<cl:quoteContent xmlns:cl=\"http://ftn.uns.ac.rs/coverLetter\"></cl:quoteContent>"
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "cl:source": {
        hasText: true,
        oneliner: true,
        mustBeBefore: ["cl:quoteContent"],
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "cl:quoteContent": {
        hasText: true,
        oneliner: true,
        asker: Xonomy.askString,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      },
      "cl:list": {
        menu: [{
          caption: "Add attribute 'ordered'",
          action: Xonomy.newAttribute,
          actionParameter: {name: "ordered", value: "false"},
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("ordered");
          }
        },{
          caption: "Add <cl:listItem>",
          action: Xonomy.newElementChild,
          actionParameter: "<cl:listItem xmlns:cl=\"http://ftn.uns.ac.rs/coverLetter\"></cl:listItem>"
        }, {
          caption: "Delete element",
          action: Xonomy.deleteElement
        }],
        attributes: {
          "ordered": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this @ordered",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },
      "cl:listItem": {
        asker: Xonomy.askString,
        hasText: true,
        oneliner: true,
        menu:[{
          caption: "Delete element",
          action: Xonomy.deleteElement
        }]
      }
    }
  };
}
