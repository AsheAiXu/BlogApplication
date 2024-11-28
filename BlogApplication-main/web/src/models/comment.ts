
import { User } from "@/models/user.ts";

export interface Comment {
    id: any;
    user_id?: number;
    article_id?: number;
    created_at?: number;
    content?: string;
    user?: User;
}
