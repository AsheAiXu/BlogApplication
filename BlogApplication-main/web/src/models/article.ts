
import { User } from "@/models/user.ts";

export interface Article {
    id?: number;
    author_id?: number;
    content?: string;
    created_at?: number;
    title?: string;
    author?: User;
}
