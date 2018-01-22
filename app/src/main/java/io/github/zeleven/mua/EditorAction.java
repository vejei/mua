package io.github.zeleven.mua;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * An action class of editor.
 */
public class EditorAction {
    private Context context; // context instance
    private EditText editText; // edittext instance

    public EditorAction() {
    }

    public EditorAction(Context context, EditText editText) {
        this.context = context;
        this.editText = editText;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    /**
     * Insert markdown heading markup, if there was nothing input, insert "#" markup to 0 position.
     * Otherwise, insert the markup at position which after line break.
     */
    public void heading() {
        int start = editText.getSelectionStart();
        String textContent = editText.getText().toString();
        int lineBreakPos = textContent.lastIndexOf("\n", start);
        int insertPos;
        if (lineBreakPos == -1) {
            insertPos = 0;
        } else {
            insertPos = lineBreakPos + 1;
        }
        editText.getText().insert(insertPos, "#");
        String afterInsert = editText.getText().toString().substring(insertPos + 1);
        if (!afterInsert.startsWith("#") && !afterInsert.startsWith(" ")) {
            editText.getText().insert(insertPos + 1, " ");
        }
    }

    /**
     * Add markdown bold markup.
     * If there was select something, add markup beside the selected text.
     * Otherwise, just add the markup and set the cursor position at the middle of markup.
     */
    public void bold() {
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();
        if (start == end) {
            editText.getText().insert(start, "****");
            editText.setSelection(start + 2);
        } else {
            editText.getText().insert(start, "**");
            editText.getText().insert(end + 2, "**");
        }
    }

    /**
     * Add markdown italic markup.
     * If there was select something, add markup beside the selected text.
     * Otherwise, just add the markup and set cursor position at the middle of markup.
     */
    public void italic() {
        // Add markdown italic markup
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();
        if (start == end) {
            editText.getText().insert(start, "**");
            editText.setSelection(start + 1);
        } else {
            editText.getText().insert(start, "*");
            editText.getText().insert(end + 1, "*");
        }
    }

    /**
     * Add markdown code markup.
     * If there was select something, add markup beside the selected text.
     * Otherwise, open a dialog to input programming language name,
     * and insert block code markup after input.
     */
    public void insertCode() {
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();
        if (start == end) { // Insert block code markup if there was nothing selected.
            AlertDialog.Builder blockCodeDialog = new AlertDialog.Builder(context);
            blockCodeDialog.setTitle(R.string.editor_dialog_title_insert_block_code);

            LayoutInflater inflater = ((AppCompatActivity) context).getLayoutInflater();
            final View view = inflater.inflate(R.layout.dialog_insert_code, null);
            blockCodeDialog.setView(view);

            blockCodeDialog.setNegativeButton(R.string.cancel,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            blockCodeDialog.setPositiveButton(R.string.dialog_btn_insert,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText langNameET = view.findViewById(R.id.lang_name);
                            String langName = langNameET.getText().toString();
                            int end = editText.getSelectionEnd();
                            editText.getText().insert(end, "\n```" + langName + "\n\n```\n");
                            editText.setSelection(end + 5 + langName.length());
                        }
                    });
            blockCodeDialog.show();
        } else { // Otherwise, insert inline code markup
            editText.getText().insert(start, "`");
            editText.getText().insert(end + 1, "`");
        }
    }

    /**
     * Add markdown block quote markup.
     */
    public void quote() {
        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();
        if (start == end) {
            editText.getText().insert(start, "> ");
            editText.setSelection(start + 2);
        } else {
            editText.getText().insert(start, "\n\n> ");
            editText.getText().insert(end + 4, "\n\n");
        }
    }

    /**
     * Add markdown ordered list markup.
     */
    public void orderedList() {
        int start = editText.getSelectionStart();
        editText.getText().insert(start, "\n1. ");
    }

    /**
     * Add markdown unordered list markup.
     */
    public void unorderedList() {
        int start = editText.getSelectionStart();
        editText.getText().insert(start, "\n* ");
    }

    /**
     * Insert markdown link markup.
     */
    public void insertLink() {
        AlertDialog.Builder linkDialog = new AlertDialog.Builder(context);
        linkDialog.setTitle(R.string.dialog_title_insert_link);
        LayoutInflater inflater = ((AppCompatActivity) context).getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_insert_link, null);
        final EditText linkDisplayTextET = view.findViewById(R.id.link_display_text);
        final EditText linkContentET = view.findViewById(R.id.link_content);

        final int start = editText.getSelectionStart();
        final int end = editText.getSelectionEnd();
        if (start < end) {
            String selectedContent = editText.getText().subSequence(start, end).toString();
            linkDisplayTextET.setText(selectedContent);
            linkContentET.requestFocus();
        }
        linkDialog.setView(view);
        linkDialog.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        linkDialog.setPositiveButton(R.string.dialog_btn_insert,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String linkDisplayText = linkDisplayTextET.getText().toString();
                String linkContent = linkContentET.getText().toString();
                if (linkDisplayText.equals("")) {
                    linkDisplayText = "Link";
                }
                String content = "[" + linkDisplayText + "]" + "(" + linkContent + ")";
                if (start == end) {
                    editText.getText().insert(start, content);
                } else {
                    editText.getText().replace(start, end, content);
                }
                if (linkContent.equals("")) {
                    editText.setSelection(content.length() - 1);
                }
            }
        });
        linkDialog.show();
    }

    /**
     * Insert markdown image markup.
     * @param displayText display text of image.
     * @param imageUri the uri of image.
     */
    public void insertImage(String displayText, String imageUri) {
        int start = editText.getSelectionStart();
        editText.getText().insert(start, "\n\n![" + displayText + "](" + imageUri + ")\n\n");
    }

    /**
     * Undo
     */
    public void undo() {
    }

    /**
     * Redo
     */
    public void redo() {
    }

    /**
     * Save file.
     */
//    public void save() {
//
//    }

    public void update(String filePath) {
        FileUtils.saveContent(new File(filePath), editText.getText().toString());
    }

    /**
     * Rename file.
     */
    public String rename(final String rootPath, final String fileName) {
        AlertDialog.Builder saveDialog = new AlertDialog.Builder(context);
        saveDialog.setTitle(R.string.dialog_title_rename_file);

        LayoutInflater inflater = ((AppCompatActivity) context).getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_save_file, null);
        final EditText fileNameET = view.findViewById(R.id.file_name);
        fileNameET.setText(fileName);

        saveDialog.setView(view);
        saveDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        saveDialog.setPositiveButton("重命名", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FileUtils.renameFile(new File(rootPath + fileName),
                        new File(rootPath + fileNameET.getText().toString()));
            }
        });
        saveDialog.show();
        return rootPath + fileNameET.getText().toString();
    }

    /**
     * Clear text in component.
     */
    public void clearAll() {
        if (editText.getText().toString().equals("")) {
            Toast.makeText(context, "内容为空", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.dialog_message_clear_all);
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    editText.setText("");
                    Toast.makeText(context, R.string.toast_message_cleared, Toast.LENGTH_SHORT).show();
                }
            });
            builder.show();
        }
    }

    /**
     * Opening docs fragment
     */
    public void checkDocs() {
        ((AppCompatActivity) context).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new MarkdownCheatsheetFragment())
                .addToBackStack(null)
                .commit();
    }

    /**
     * statistics dialog
     */
    public void statistics() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("统计信息");

        LayoutInflater inflater = ((AppCompatActivity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_statistics, null);
        TextView textLengthTV = view.findViewById(R.id.text_length);
        textLengthTV.setText(editText.getText().length() + "");

        builder.setView(view);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    /**
     * Toggle keyboard according flag. If flag equals 0, hide or close keyboard. Otherwise, open.
     * @param flag A flag which indicate it should open keyboard or not.
     */
    public void toggleKeyboard(int flag) {
        InputMethodManager inputMethodManager = (InputMethodManager)
                (context.getSystemService(Context.INPUT_METHOD_SERVICE));
        if (inputMethodManager != null) {
            if (flag == 0) {
                inputMethodManager.hideSoftInputFromWindow(((AppCompatActivity) context)
                        .getCurrentFocus().getWindowToken(), 0);
            } else {
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        }
    }
}
