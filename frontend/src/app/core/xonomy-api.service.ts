import { Injectable } from '@angular/core';
declare const Xonomy: any;
import {reviewTemplate} from '../dashboard/review/add-review/reviewTemplate'
import {UserService} from './user.service'

@Injectable({
  providedIn: 'root'
})
export class XonomyApiService {
  static metadataPrefix = "pred:";
  static scientificPublicationPrefix = "https://github.com/milica152/XML_tim27";
  static personPrefix = "https://github.com/milica152/XML_tim27/person/";

  constructor(private userService: UserService) {
  }

  askImage = function (defaultString, askerParameter, jsMe) {
    return "<form>\n" +
      "  <label for=\"img\">Select image:</label>\n" +
      "  <input type=\"file\" id=\"img\" name=\"img\" accept=\"image/*\">\n" +
      "  <input type=\"button\"  value = \"Ok\" onclick='returnImage()'/>\n" +
      "</form>"
  }

  public scientificPublicationSpecification = {
    elements: {
      "title": {
        mustBeBefore: ["authors", "abstract", "chapters", "references"],
        asker: Xonomy.askString
      },
      "authors": {
        menu: [{
          caption: "Add author",
          action: Xonomy.newElementChild,
          actionParameter: `<author xmlns:sp=\"https://github.com/milica152/XML_tim27\">
              <name>Insert author's first name</name>
              <surname>Insert author's last name</surname>
              <profession>Insert author's profession</profession>
              <contact>Insert author's contact</contact></author>`
        }
        ]
      },
      "author": {
        menu: [{
          caption: "Delete element",
          action: Xonomy.deleteElement,
          hideIf: function (jsElement) {
            return (jsElement.parent().getChildElements("author").length == 1);
          }
        }]
      },
      "abstract": {
        mustBeBefore: ["chapters", "references"],
        menu: [{
          caption: "Add paragraph",
          action: Xonomy.newElementChild,
          actionParameter: "<paragraph xmlns:sp=\"https://github.com/milica152/XML_tim27\">Insert content</paragraph>"
        }]
      },
      "paragraph": {
        hasText: true,
        menu: [{
          caption: "Add id attribute",
          action: Xonomy.newAttribute,
          actionParameter: {name: "sp:id", value: ""},
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("sp:id");
          }
        }, {
          caption: "Add style attribute",
          action: Xonomy.newAttribute,
          actionParameter: {name: "style", value: ""},
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("style");
          }
        },
          {
            caption: "Delete element",
            action: Xonomy.deleteElement,
            hideIf: function (jsElement) {
              return (jsElement.parent().getChildElements("paragraph").length == 1);
            }
          }],
        attributes: {
          "sp:id": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this id attribute",
              action: Xonomy.deleteAttribute
            }]
          },
          "style": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this style attribute",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },
      "chapters": {
        mustBeBefore: ["references"],
        menu: [{
          caption: "Add chapter",
          action: Xonomy.newElementChild,
          actionParameter: "<chapter xmlns:sp=\"https://github.com/milica152/XML_tim27\">" +
            "<title>Insert chapter's title</title>" +
            "<text><paragraph>Insert content</paragraph></text></chapter>"
        }]
      },
      "chapter": {
        menu: [{
          caption: "Add id attribute",
          action: Xonomy.newAttribute,
          actionParameter: {name: "sp:id", value: ""},
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("sp:id");
          }
        },
          {
            caption: "Delete element",
            action: Xonomy.deleteElement,
            hideIf: function (jsElement) {
              return (jsElement.parent().getChildElements("chapter").length == 1);
            }

          },
          {
            caption: "Add text",
            action: Xonomy.newElementChild,
            actionParameter: `<text><paragraph>Insert content</paragraph></text>`
          },
          {
            caption: "Add image",
            action: Xonomy.newElementChild,
            actionParameter: `<image><description>Insert image description</description>
                              <concreteImage>Choose image</concreteImage></image>`
          },
          {
            caption: "Add list",
            action: Xonomy.newElementChild,
            actionParameter: `<list><listItem>Insert list item</listItem></list>`
          },
          {
            caption: "Add table",
            action: Xonomy.newElementChild,
            actionParameter: `<table><row><cell>Insert content</cell></row></table>`
          },
          {
            caption: "Add code block",
            action: Xonomy.newElementChild,
            actionParameter: `<codeBlock>Insert code</codeBlock>`
          },
          {
            caption: "Add reference pointer",
            action: Xonomy.newElementChild,
            actionParameter: `<referencePointer>Insert reference</referencePointer>`
          }

        ],
        attributes: {
          "sp:id": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this id attribute",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },
      "text": {
        menu: [{
          caption: "Add paragraph",
          action: Xonomy.newElementChild,
          actionParameter: `<paragraph>Insert content</paragraph>`
        }
        ]
      },
      "concreteImage": {
        asker: this.askImage
      },
      "image": {
        menu: [{
          caption: "Add id attribute",
          action: Xonomy.newAttribute,
          actionParameter: {name: "sp:id", value: ""},
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("sp:id");
          }
        },
          {
            caption: "Delete element",
            action: Xonomy.deleteElement
          }
        ], attributes: {
          "sp:id": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this id attribute",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },
      "list": {
        menu: [{
          caption: "Add list item",
          action: Xonomy.newElementChild,
          actionParameter: `<listItem>Insert list item</listItem>`
        },
          {
            caption: "Add id attribute",
            action: Xonomy.newAttribute,
            actionParameter: {name: "sp:id", value: ""},
            hideIf: function (jsElement) {
              return jsElement.hasAttribute("sp:id");
            }
          },
          {
            caption: "Add style attribute",
            action: Xonomy.newAttribute,
            actionParameter: {name: "style", value: ""},
            hideIf: function (jsElement) {
              return jsElement.hasAttribute("style");
            }
          },
          {
            caption: "Add isOrdered attribute",
            action: Xonomy.newAttribute,
            actionParameter: {name: "isOrdered", value: ""},
            hideIf: function (jsElement) {
              return jsElement.hasAttribute("isOrdered");
            }
          },
          {
            caption: "Delete element",
            action: Xonomy.deleteElement
          }
        ],
        attributes: {
          "sp:id": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this id attribute",
              action: Xonomy.deleteAttribute
            }]
          },
          "style": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this style attribute",
              action: Xonomy.deleteAttribute
            }]
          },
          "isOrdered": {
            asker: Xonomy.askPicklist,
            askerParameter: [
              {value: "true", caption: "yes"},
              {value: "false", caption: "no"}
            ],
            menu: [{
              caption: "Delete this isOrdered attribute",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },
      "listItem": {
        menu: [{
          caption: "Delete element",
          action: Xonomy.deleteElement,
          hideIf: function (jsElement) {
            return (jsElement.parent().getChildElements("listItem").length == 1);
          }
        }]
      },
      "table": {
        menu: [{
          caption: "Add row",
          action: Xonomy.newElementChild,
          actionParameter: `<row><cell>Insert content</cell></row>`
        },
          {
            caption: "Add id attribute",
            action: Xonomy.newAttribute,
            actionParameter: {name: "sp:id", value: ""},
            hideIf: function (jsElement) {
              return jsElement.hasAttribute("sp:id");
            }
          },
          {
            caption: "Add width attribute",
            action: Xonomy.newAttribute,
            actionParameter: {name: "width", value: ""},
            hideIf: function (jsElement) {
              return jsElement.hasAttribute("width");
            }
          },
          {
            caption: "Add height attribute",
            action: Xonomy.newAttribute,
            actionParameter: {name: "height", value: ""},
            hideIf: function (jsElement) {
              return jsElement.hasAttribute("height");
            }
          },
          {
            caption: "Add measuring unit attribute",
            action: Xonomy.newAttribute,
            actionParameter: {name: "measuringUnit", value: ""},
            hideIf: function (jsElement) {
              return jsElement.hasAttribute("measuringUnit");
            }
          },
          {
            caption: "Delete element",
            action: Xonomy.deleteElement
          }
        ],
        attributes: {
          "sp:id": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this id attribute",
              action: Xonomy.deleteAttribute
            }]
          },
          "width": {
            asker: Xonomy.askInteger,
            menu: [{
              caption: "Delete this style attribute",
              action: Xonomy.deleteAttribute
            }]
          },
          "height": {
            asker: Xonomy.askInteger,
            menu: [{
              caption: "Delete this isOrdered attribute",
              action: Xonomy.deleteAttribute
            }]
          },
          "measuringUnit": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this style attribute",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },
      "row": {
        menu: [{
          caption: "Add cell",
          action: Xonomy.newElementChild,
          actionParameter: `<cell>Insert content</cell>`
        },
          {
            caption: "Delete element",
            action: Xonomy.deleteElement,
            hideIf: function (jsElement) {
              return (jsElement.parent().getChildElements("row").length == 1);
            }

          }
        ]
      },
      "cell": {
        menu: [{
          caption: "Add style attribute",
          action: Xonomy.newAttribute,
          actionParameter: {name: "style", value: ""},
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("style");
          }
        },
          {
            caption: "Delete element",
            action: Xonomy.deleteElement,
            hideIf: function (jsElement) {
              return (jsElement.parent().getChildElements("cell").length == 1);
            }

          }
        ],
        attributes: {
          "style": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this style attribute",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },
      "codeBlock": {
        menu: [{
          caption: "Add id attribute",
          action: Xonomy.newAttribute,
          actionParameter: {name: "sp:id", value: ""},
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("sp:id");
          }
        },
          {
            caption: "Delete element",
            action: Xonomy.deleteElement
          }
        ],
        attributes: {
          "sp:id": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this id attribute",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },
      "referencePointer": {
        menu: [{
          caption: "Add id attribute",
          action: Xonomy.newAttribute,
          actionParameter: {name: "sp:id", value: ""},
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("sp:id");
          }
        },
          {
            caption: "Delete element",
            action: Xonomy.deleteElement
          }
        ],
        attributes: {
          "sp:id": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this id attribute",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },
      "references": {
        menu: [{
          caption: "Add reference",
          action: Xonomy.newElementChild,
          actionParameter: "<reference xmlns:sp=\"https://github.com/milica152/XML_tim27\">" +
            "<url>Insert reference's url</url>" +
            "<name>Insert reference's name</name>" +
            "<year>Insert reference's year of publication</year>" +
            "<authorName>Insert reference's author name</authorName>" +
            "</reference>"
        }]
      },
      "reference": {
        menu: [{
          caption: "Add id attribute",
          action: Xonomy.newAttribute,
          actionParameter: {name: "sp:id", value: ""},
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("sp:id");
          }
        },
          {
            caption: "Delete element",
            action: Xonomy.deleteElement
          }],
        attributes: {
          "sp:id": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this id attribute",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      }
    }
  };

  public coverLetterSpecification = {
    elements: {
      "coverLetter": {
        mustBeBefore: ["paperTitle", "date", "author", "paragraph", "signature"],
        menu: [{
          caption: "Add paragraph",
          action: Xonomy.newElementChild,
          actionParameter: "<paragraph>Insert content</paragraph>"
        }]
      },
      "paperTitle": {
        mustBeBefore: ["date", "author", "paragraph", "signature"],
        asker: Xonomy.askString
      },
      "date": {
        mustBeBefore: ["author", "paragraph", "signature"],
        asker: Xonomy.askDate
      },
      "author": {
        menu: [{
          caption: "Add id attribute",
          action: Xonomy.newAttribute,
          actionParameter: {name: "sp:id", value: ""},
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("sp:id");
          }
        }],
        attributes: {
          "sp:id": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this id attribute",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },
      "paragraph": {
        mustBeBefore: ["signature"],
        mustBeAfter: ["author"],
        hasText: true,
        menu: [
          {
            caption: "Add id attribute",
            action: Xonomy.newAttribute,
            actionParameter: {name: "sp:id", value: ""},
            hideIf: function (jsElement) {
              return jsElement.hasAttribute("sp:id");
            }
          },
          {
            caption: "Add style attribute",
            action: Xonomy.newAttribute,
            actionParameter: {name: "style", value: ""},
            hideIf: function (jsElement) {
              return jsElement.hasAttribute("style");
            }
          },
          {
            caption: "Delete element",
            action: Xonomy.deleteElement,
            hideIf: function (jsElement) {
              return (jsElement.parent().getChildElements("paragraph").length == 1);
            }
          }],
        attributes: {
          "sp:id": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this id attribute",
              action: Xonomy.deleteAttribute
            }]
          },
          "style": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this style attribute",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },
      "sincerely": {
        asker: Xonomy.askSincerely
      },
      "signatureImage": {
        asker: this.askImage
      }
    }
  };

  getReviewTemplate(paperTitle: any): string {
    let currentUser = this.userService.getLoggedUser();
    return reviewTemplate(currentUser, paperTitle);
  }

  public reviewSpecification = {
    elements: {
      "title": {
        mustBefore: ["id", "reviewer", "authors", "questionnaire", "comments", "paperRating", "recommendation"],
        menu: [
          {
            caption: "Add style attribute",
            action: Xonomy.newAttribute,
            actionParameter: {name: "style", value: ""},
            hideIf: function (jsElement) {
              return jsElement.hasAttribute("style");
            }
          },
          {
            caption: "Add level attribute",
            action: Xonomy.newAttribute,
            actionParameter: {name: "level", value: ""},
            hideIf: function (jsElement) {
              return jsElement.hasAttribute("level");
            }
          }
        ],
        attributes: {
          "style": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this style attribute",
              action: Xonomy.deleteAttribute
            }]
          },
          "level": {
            asker: Xonomy.askInteger,
            menu: [{
              caption: "Delete this level attribute",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      }
      ,
      "authors": {
        mustBeBefore: ["questionnaire", "comments", "paperRating", "recommendation"],
        menu: [
          {caption: "Add author",
            action: Xonomy.newElementChild,
            actionParameter: `<author xmlns:sp=\"https://github.com/milica152/XML_tim27\">
              <name>Insert author's first name</name>
              <surname>Insert author's last name</surname>
              <profession>Insert author's profession</profession>
              <contact>Insert author's contact</contact></author>`
          }
        ]
      },
      "author": {
        menu: [{
          caption: "Delete element",
          action: Xonomy.deleteElement,
          hideIf: function (jsElement) {
            return (jsElement.parent().getChildElements("author").length == 1);
          }
        },
          {
            caption: "Add id attribute",
            action: Xonomy.newAttribute,
            actionParameter: {name: "sp:id", value: ""},
            hideIf: function (jsElement) {
              return jsElement.hasAttribute("sp:id");
            }
          }
        ],
        attributes:{
          "sp:id": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this id attribute",
              action: Xonomy.deleteAttribute
            }]
          }
        }
      },
      "questionnaire": {
        menu: [
          {caption: "Add item",
            action: Xonomy.newElementChild,
            actionParameter: `<item><question>Insert question</question><answer>Insert answer</answer>
            <comment_example>Insert comment or example</comment_example></item>`
          }
        ]
      },
      "answer":{
        asker: Xonomy.askPicklist,
        askerParameter: [
          {value: "exceeds_expectations", caption: "Prevazilazi očekivanja"},
          {value: "meets_expectations", caption: "Ispunjava očekivanja"},
          {value: "needs_improvement", caption: "Potrebno je poboljšati"},
          {value: "unacceptable", caption: "Neprihvatljivo"}
        ]
      },
      "item":{
        menu:[
          {
            caption: "Delete element",
            action: Xonomy.deleteElement,
            hideIf: function (jsElement) {
              return (jsElement.parent().getChildElements("item").length == 1);
            }
          }
        ]
      },
      "comment_example":{
        menu: [{
          caption: "Add id attribute",
          action: Xonomy.newAttribute,
          actionParameter: {name: "sp:id", value: ""},
          hideIf: function (jsElement) {
            return jsElement.hasAttribute("sp:id");
          }
        }],
        attributes: {
          "sp:id": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this id attribute",
              action: Xonomy.deleteAttribute
            }]
          }}
      },
      "comments":{
        menu: [
          {caption: "Add comment",
            action: Xonomy.newElementChild,
            actionParameter: `<comment>Insert comment</comment>`
          }
        ]
      },
      "comment":{
        menu:[
          {
            caption: "Delete element",
            action: Xonomy.deleteElement
          },
          {
            caption: "Add id attribute",
            action: Xonomy.newAttribute,
            actionParameter: {name: "sp:id", value: ""},
            hideIf: function (jsElement) {
              return jsElement.hasAttribute("sp:id");
            }
          }
        ],
        attributes: {
          "sp:id": {
            asker: Xonomy.askString,
            menu: [{
              caption: "Delete this id attribute",
              action: Xonomy.deleteAttribute
            }]
          }}

      },
      "paperRating":{
        asker: Xonomy.askPicklist,
        askerParameter: [
          {value: "outstanding", caption: "outstanding"},
          {value: "very_good", caption: "very good"},
          {value: "good", caption: "good"},
          {value: "fair", caption: "fair"},
          {value: "poor", caption: "poor"}
        ]
      },
      "recommendation":{
        asker: Xonomy.askPicklist,
        askerParameter: [
          {value: "accept", caption: "accept"},
          {value: "return", caption: "return"},
          {value: "reject", caption: "reject"}
        ]
      }




    }

  }
}
